package com.example.ordermodule.service.impl;

import com.example.ordermodule.dto.EmployeeDTO;
import com.example.ordermodule.dto.SearchTermDTO;
import com.example.ordermodule.dto.WarehouseDTO;
import com.example.ordermodule.dto.WarehouseReceiptDTO;
import com.example.ordermodule.enity.Bill;
import com.example.ordermodule.enity.Inventory;
import com.example.ordermodule.enity.WareHouseReceipt;
import com.example.ordermodule.enity.WareHouseReceiptDetail;
import com.example.ordermodule.repository.BillRepository;
import com.example.ordermodule.repository.InventoryRepository;
import com.example.ordermodule.repository.WarehouseReceiptRepository;
import com.example.ordermodule.service.WareHouseReceiptService;
import com.example.ordermodule.utils.RabbitMQSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class WarehouseReceiptServiceImpl implements WareHouseReceiptService {
    @Autowired
    private WarehouseReceiptRepository warehouseReceiptRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private RabbitMQSender rabbitMQSender;

    public List<Object> getEmployeeAndWareHouse() throws JsonProcessingException {
        List<Object> resObjects = new ArrayList<>();
        List<WarehouseDTO> wareHouses = rabbitMQSender.sendToGetListWarehouse();
        List<EmployeeDTO> employees = rabbitMQSender.sendToGetListEmployee("EMPLOYEE");;
        resObjects.add(wareHouses);
        resObjects.add(employees);
        return resObjects;
    }

    public WareHouseReceipt save(WareHouseReceipt wareHouseReceipt) {
        WareHouseReceipt check = warehouseReceiptRepository.findByCode(wareHouseReceipt.getCode());
        if (wareHouseReceipt.getId() != null) {
            if (check != null && check.getId() != null && !check.getId().equals(wareHouseReceipt.getId())) {
                return null;
            }
        } else {
            if (check != null && check.getId() != null) {
                return null;
            }
        }
        wareHouseReceipt.getWareHouseReceiptDetails().forEach(n -> {
            n.setId(null);
        });
        wareHouseReceipt = warehouseReceiptRepository.save(wareHouseReceipt);
        // lưu hàng tồn kho
        List<WareHouseReceipt> wareHouseReceipts = warehouseReceiptRepository.loadWarehouseReceiptByIdWar(wareHouseReceipt.getIdWar());
        List<WareHouseReceiptDetail> wareHouseReceiptDetails = new ArrayList<>();
        for (int i = 0; i < wareHouseReceipts.size(); i++) {
            for (int j = 0; j < wareHouseReceipts.get(i).getWareHouseReceiptDetails().size(); j++) {
                wareHouseReceipts.get(i).getWareHouseReceiptDetails().get(j).setType(wareHouseReceipts.get(i).getType());
                wareHouseReceiptDetails.add(wareHouseReceipts.get(i).getWareHouseReceiptDetails().get(j));
            }
        }
        Map<Long, Inventory> maps = new HashMap<>();
        for (int i = 0; i < wareHouseReceiptDetails.size(); i++) {
            if (maps.containsKey(wareHouseReceiptDetails.get(i).getIdPro())) {
                Inventory inventory = maps.get(wareHouseReceiptDetails.get(i).getIdPro());
                if (wareHouseReceiptDetails.get(i).getType() == 1) {
                    inventory.setQuantity(inventory.getQuantity() + wareHouseReceiptDetails.get(i).getQuantity());
                } else {
                    inventory.setQuantity(inventory.getQuantity() - wareHouseReceiptDetails.get(i).getQuantity());
                }
                maps.put(inventory.getIdPro(), inventory);
            } else {
                Inventory inventory = new Inventory();
                inventory.setIdWar(wareHouseReceipt.getIdWar());
                inventory.setIdPro(wareHouseReceiptDetails.get(i).getIdPro());
                inventory.setNamePro(wareHouseReceiptDetails.get(i).getNamePro());
                if (wareHouseReceiptDetails.get(i).getType() == 1) {
                    inventory.setQuantity(wareHouseReceiptDetails.get(i).getQuantity());
                } else {
                    inventory.setQuantity(-wareHouseReceiptDetails.get(i).getQuantity());
                }
                maps.put(inventory.getIdPro(), inventory);
            }
        }
        inventoryRepository.removeInventoriesByIdWar(wareHouseReceipt.getIdWar());
        inventoryRepository.saveAll(new ArrayList<>(maps.values()));
        return wareHouseReceipt;
    }

    public WareHouseReceipt createExport(WareHouseReceipt wareHouseReceipt) {
//        WareHouseReceipt check = warehouseReceiptRepository.findByCode(wareHouseReceipt.getCode());
//        if (wareHouseReceipt.getId() != null) {
//            if (check != null && check.getId() != null && !check.getId().equals(wareHouseReceipt.getId())) {
//                return null;
//            }
//        } else {
//            if (check != null && check.getId() != null) {
//                return null;
//            }
//        }
        Bill bill = billRepository.findById(wareHouseReceipt.getIdBil()).get();
        bill.setToDate(wareHouseReceipt.getDate());
        bill.setStatus(1);
        billRepository.updateOrder(bill.getStatus(), bill.getToDate(), bill.getId());
        wareHouseReceipt.getWareHouseReceiptDetails().forEach(n -> {
            n.setId(null);
        });
        wareHouseReceipt = warehouseReceiptRepository.save(wareHouseReceipt);
        // lưu hàng tồn kho
        List<WareHouseReceipt> wareHouseReceipts = warehouseReceiptRepository.loadWarehouseReceiptByIdWar(wareHouseReceipt.getIdWar());
        List<WareHouseReceiptDetail> wareHouseReceiptDetails = new ArrayList<>();
        for (int i = 0; i < wareHouseReceipts.size(); i++) {
            for (int j = 0; j < wareHouseReceipts.get(i).getWareHouseReceiptDetails().size(); j++) {
                wareHouseReceipts.get(i).getWareHouseReceiptDetails().get(j).setType(wareHouseReceipts.get(i).getType());
                wareHouseReceiptDetails.add(wareHouseReceipts.get(i).getWareHouseReceiptDetails().get(j));
            }
        }
        Map<Long, Inventory> maps = new HashMap<>();
        for (int i = 0; i < wareHouseReceiptDetails.size(); i++) {
            if (maps.containsKey(wareHouseReceiptDetails.get(i).getIdPro())) {
                Inventory inventory = maps.get(wareHouseReceiptDetails.get(i).getIdPro());
                if (wareHouseReceiptDetails.get(i).getType() == 1) {
                    inventory.setQuantity(inventory.getQuantity() + wareHouseReceiptDetails.get(i).getQuantity());
                } else {
                    inventory.setQuantity(inventory.getQuantity() - wareHouseReceiptDetails.get(i).getQuantity());
                }
                maps.put(inventory.getIdPro(), inventory);
            } else {
                Inventory inventory = new Inventory();
                inventory.setIdWar(wareHouseReceipt.getIdWar());
                inventory.setIdPro(wareHouseReceiptDetails.get(i).getIdPro());
                inventory.setNamePro(wareHouseReceiptDetails.get(i).getNamePro());
                if (wareHouseReceiptDetails.get(i).getType() == 1) {
                    inventory.setQuantity(wareHouseReceiptDetails.get(i).getQuantity());
                } else {
                    inventory.setQuantity(-wareHouseReceiptDetails.get(i).getQuantity());
                }
                maps.put(inventory.getIdPro(), inventory);
            }
        }
        inventoryRepository.removeInventoriesByIdWar(wareHouseReceipt.getIdWar());
        inventoryRepository.saveAll(new ArrayList<>(maps.values()));
        return wareHouseReceipt;
    }

    @Override
    public Page<WarehouseReceiptDTO> loadPagination(SearchTermDTO searchTermDTO, Pageable pageable) {
        return warehouseReceiptRepository.loadPagination(searchTermDTO, pageable);
    }

    @Override
    public WareHouseReceipt findOne(Long id) {
        return warehouseReceiptRepository.findById(id).get();
    }

    @Override
    public Page<WarehouseReceiptDTO> loadDetailPagination(SearchTermDTO searchTermDTO, Pageable pageable) {
        return warehouseReceiptRepository.loadDetailPagination(searchTermDTO, pageable);
    }
}
