package com.example.commonmodule.service.impl;

import com.example.commonmodule.dto.*;
import com.example.commonmodule.entity.Area;
import com.example.commonmodule.entity.Policy;
import com.example.commonmodule.entity.WareHouse;
import com.example.commonmodule.repository.AreaRepository;
import com.example.commonmodule.repository.PolicyRepository;
import com.example.commonmodule.repository.WareHouseRepository;
import com.example.commonmodule.service.WareHouseService;
import com.example.commonmodule.utils.RabbitMQSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class WareHouseServiceImpl implements WareHouseService {
    private final AreaRepository areaRepository;
    private final WareHouseRepository wareHouseRepository;
    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    public WareHouseServiceImpl(AreaRepository areaRepository,
                                WareHouseRepository wareHouseRepository) {
        this.areaRepository = areaRepository;
        this.wareHouseRepository = wareHouseRepository;
    }

    @Override
    public List<Area> getAllArea(SearchTermDTO searchTermDTO) {
        return this.areaRepository.getAllArea(searchTermDTO);
    }

    @Override
    public WareHouse save(WareHouse wareHouse) {
        WareHouse check = wareHouseRepository.findByName(wareHouse.getNameWar());
        if (wareHouse.getId() != null) {
            if (check != null && check.getId() != null && !check.getId().equals(wareHouse.getId())) {
                return null;
            }
        } else {
            if (check != null && check.getId() != null) {
                return null;
            }
        }
        wareHouse = wareHouseRepository.save(wareHouse);
        return wareHouse;
    }

    @Override
    public Page<WareHouse> loadDataAll(Pageable pageable, SearchTermDTO searchTermDTO) {
        return this.wareHouseRepository.loadAllData(searchTermDTO, pageable);
    }

    @Override
    public List<WarehouseDTO> loadWarehouse(SearchTermDTO searchTermDTO) throws JsonProcessingException {
        List<WareHouse> tmp = new ArrayList<>();
        if (Strings.isNullOrEmpty(searchTermDTO.getCodeProvince())) {
            return new ArrayList<>();
        }
        if (searchTermDTO.getCodeDistrict() == null) {
            tmp = this.wareHouseRepository.findWarehouseByIdPro(searchTermDTO.getCodeProvince());
        } else {
            tmp = this.wareHouseRepository.findWarehouseByIdProAndIdDis(searchTermDTO.getCodeProvince(), searchTermDTO.getCodeDistrict());
        }
        List<WarehouseReceiptDTO> wareHouseReceipts = rabbitMQSender.sendToGetListWarehouse(tmp.stream().map(WareHouse::getId).collect(Collectors.toList()), searchTermDTO.getListID());
        Map<Long, WarehouseDTO> rs = new HashMap<>();
        for (int i = 0; i < wareHouseReceipts.size(); i++) {
            if (!rs.containsKey(wareHouseReceipts.get(i).getIdWar())) {
                WarehouseDTO warehouseDTO = new WarehouseDTO();
                warehouseDTO.setId(wareHouseReceipts.get(i).getIdWar());
                WareHouse wareHouse = tmp.stream().filter(n -> n.getId().equals(warehouseDTO.getId())).findFirst().get();
                warehouseDTO.setIdProvince(wareHouse.getIdPro());
                warehouseDTO.setIdDistrict(wareHouse.getIdDis());
                warehouseDTO.setAddress(wareHouse.getAddress());
                warehouseDTO.setAvailable(
                    wareHouseReceipts.get(i).getWareHouseReceiptDetails().stream().
                        map(WarehouseReceiptDetailDTO::getIdPro).filter(n -> searchTermDTO.getListID().contains(n)).collect(Collectors.toSet())
                );
                rs.put(warehouseDTO.getId(), warehouseDTO);
            } else {
                WarehouseDTO warehouseDTO = rs.get(wareHouseReceipts.get(i).getIdWar());
                warehouseDTO.getAvailable().addAll(wareHouseReceipts.get(i).getWareHouseReceiptDetails().stream().
                        map(WarehouseReceiptDetailDTO::getIdPro).filter(n -> searchTermDTO.getListID().contains(n)).collect(Collectors.toSet()));
            }
        }
        for (Long key: rs.keySet()) {
            WarehouseDTO warehouseDTO = rs.get(key);
            warehouseDTO.setUnavailable(searchTermDTO.getListID().stream().
                    filter(n -> !warehouseDTO.getAvailable().contains(n)).collect(Collectors.toList()));
            rs.put(key, warehouseDTO);
        }
        return new ArrayList<>(rs.values());
    }

    @Override
    public WarehouseDTO calculateFee(SearchTermDTO searchTermDTO) throws IOException {
        List<WarehouseDTO> warehouseInProvinces = this.wareHouseRepository.loadWarehouseHaveProduct(searchTermDTO);
        if (warehouseInProvinces.size() == 0) {
            return new WarehouseDTO();
        }
        Map<String, List<WarehouseDTO>> mapAddress = new HashMap<>();
        List<String> addressWareHouses = new ArrayList<>();
        for (WarehouseDTO tmp: warehouseInProvinces) {
            if (!mapAddress.containsKey(tmp.getAddress())) {
                List<WarehouseDTO> value = new ArrayList<>();
                value.add(tmp);
                mapAddress.put(tmp.getAddress(), value);
                addressWareHouses.add(tmp.getAddress());
            } else {
                List<WarehouseDTO> value = mapAddress.get(tmp.getAddress());
                value.add(tmp);
                mapAddress.put(tmp.getAddress(), value);
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        // goolge 2: TH 1 cửa hàng tất cả sản phẩm
        GoogleAPI googleAPI = objectMapper.readValue("{\n" +
                "   \"destination_addresses\" : [\n" +
                "      \"Ngõ 3 Hoàng Đạo Thành, Kim Giang, Thanh Xuân, Hà Nội, Vietnam\",\n" +
                "      \"Đường Khương Đình, Khương Đình, Thanh Xuân, Hà Nội, Vietnam\"\n" +
                "   ],\n" +
                "   \"origin_addresses\" : [ \"Khương Mai, Thanh Xuân, Hanoi, Vietnam\" ],\n" +
                "   \"rows\" : [\n" +
                "      {\n" +
                "         \"elements\" : [\n" +
                "            {\n" +
                "               \"distance\" : {\n" +
                "                  \"text\" : \"3.3 km\",\n" +
                "                  \"value\" : 3254\n" +
                "               },\n" +
                "               \"duration\" : {\n" +
                "                  \"text\" : \"12 mins\",\n" +
                "                  \"value\" : 691\n" +
                "               },\n" +
                "               \"status\" : \"OK\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"distance\" : {\n" +
                "                  \"text\" : \"3.7 km\",\n" +
                "                  \"value\" : 3734\n" +
                "               },\n" +
                "               \"duration\" : {\n" +
                "                  \"text\" : \"10 mins\",\n" +
                "                  \"value\" : 581\n" +
                "               },\n" +
                "               \"status\" : \"OK\"\n" +
                "            }\n" +
                "         ]\n" +
                "      }\n" +
                "   ],\n" +
                "   \"status\" : \"OK\"\n" +
                "}\n{\n" +
                "   \"destination_addresses\" : [\n" +
                "      \"Ngõ 3 Hoàng Đạo Thành, Kim Giang, Thanh Xuân, Hà Nội, Vietnam\",\n" +
                "      \"Đường Khương Đình, Khương Đình, Thanh Xuân, Hà Nội, Vietnam\"\n" +
                "   ],\n" +
                "   \"origin_addresses\" : [ \"Khương Mai, Thanh Xuân, Hanoi, Vietnam\" ],\n" +
                "   \"rows\" : [\n" +
                "      {\n" +
                "         \"elements\" : [\n" +
                "            {\n" +
                "               \"distance\" : {\n" +
                "                  \"text\" : \"3.3 km\",\n" +
                "                  \"value\" : 3254\n" +
                "               },\n" +
                "               \"duration\" : {\n" +
                "                  \"text\" : \"12 mins\",\n" +
                "                  \"value\" : 691\n" +
                "               },\n" +
                "               \"status\" : \"OK\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"distance\" : {\n" +
                "                  \"text\" : \"3.7 km\",\n" +
                "                  \"value\" : 3734\n" +
                "               },\n" +
                "               \"duration\" : {\n" +
                "                  \"text\" : \"10 mins\",\n" +
                "                  \"value\" : 581\n" +
                "               },\n" +
                "               \"status\" : \"OK\"\n" +
                "            }\n" +
                "         ]\n" +
                "      }\n" +
                "   ],\n" +
                "   \"status\" : \"OK\"\n" +
                "}\n", GoogleAPI.class);

        // google 3: TH cửa hàng không có đủ hàng
//        GoogleAPI googleAPI = objectMapper.readValue("{\n" +
//                "   \"destination_addresses\" : [\n" +
//                "      \"Ngõ 3 Hoàng Đạo Thành, Kim Giang, Thanh Xuân, Hà Nội, Vietnam\",\n" +
//                "      \"Đường Khương Đình, Khương Đình, Thanh Xuân, Hà Nội, Vietnam\"\n" +
//                "   ],\n" +
//                "   \"origin_addresses\" : [ \"Ha Dinh, Thanh Xuân, Hanoi, Vietnam\" ],\n" +
//                "   \"rows\" : [\n" +
//                "      {\n" +
//                "         \"elements\" : [\n" +
//                "            {\n" +
//                "               \"distance\" : {\n" +
//                "                  \"text\" : \"1.1 km\",\n" +
//                "                  \"value\" : 1121\n" +
//                "               },\n" +
//                "               \"duration\" : {\n" +
//                "                  \"text\" : \"4 mins\",\n" +
//                "                  \"value\" : 217\n" +
//                "               },\n" +
//                "               \"status\" : \"OK\"\n" +
//                "            },\n" +
//                "            {\n" +
//                "               \"distance\" : {\n" +
//                "                  \"text\" : \"1.2 km\",\n" +
//                "                  \"value\" : 1236\n" +
//                "               },\n" +
//                "               \"duration\" : {\n" +
//                "                  \"text\" : \"4 mins\",\n" +
//                "                  \"value\" : 227\n" +
//                "               },\n" +
//                "               \"status\" : \"OK\"\n" +
//                "            }\n" +
//                "         ]\n" +
//                "      }\n" +
//                "   ],\n" +
//                "   \"status\" : \"OK\"\n" +
//                "}", GoogleAPI.class);

        List<Elements> kcClientToWareHouse = googleAPI.getRows().get(0).getElements();
        int min = Integer.MAX_VALUE;
        int imin = 0;
        String km = "";
        for (int i = 0; i < kcClientToWareHouse.size(); i++) {
            Duration duration = kcClientToWareHouse.get(i).getDuration();
            int timeValue = Integer.parseInt(duration.getValue());
            if (timeValue < min) {
                min = timeValue;
                imin = i;
                km = kcClientToWareHouse.get(i).getDistance().getText();
            }
        }
        // fake imin value because no use Google Map API
        imin = 0;
        WarehouseDTO warehouseDTO = new WarehouseDTO();
        warehouseDTO.setDistance(km);
        warehouseDTO.setId(mapAddress.get(addressWareHouses.get(imin)).get(0).getId());
        warehouseDTO.setAddress(mapAddress.get(addressWareHouses.get(imin)).get(0).getAddress());
        warehouseDTO.setAvailable(mapAddress.get(addressWareHouses.get(imin)).stream().map(WarehouseDTO::getIdProduct).collect(Collectors.toSet()));
        warehouseDTO.setUnavailable(searchTermDTO.getListID().stream().
                filter(n -> !warehouseDTO.getAvailable().contains(n)).collect(Collectors.toList()));
        List<Policy> policies = policyRepository.getAll();
        policies = policies.stream().sorted(Comparator.comparingInt(Policy::getFromDis)).collect(Collectors.toList());
        double dis = Double.parseDouble(km.split(" ")[0]);
        int ipol = -1;
        for (int i = 0; i < policies.size(); i++) {
            if (dis >= policies.get(i).getFromDis()) {
                ipol = i;
            } else {
                break;
            }
        }
        if (ipol >= 0) {
            warehouseDTO.setFee(policies.get(ipol).getAmount());
            warehouseDTO.setIdPol(policies.get(ipol).getId());
        } else {
            warehouseDTO.setFee((double) 0);
            warehouseDTO.setIdPol(null);
        }

        return warehouseDTO;
    }
}
