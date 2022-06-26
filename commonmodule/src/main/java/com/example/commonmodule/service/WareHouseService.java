package com.example.commonmodule.service;

import com.example.commonmodule.dto.SearchTermDTO;
import com.example.commonmodule.dto.WarehouseDTO;
import com.example.commonmodule.entity.Area;
import com.example.commonmodule.entity.WareHouse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface WareHouseService {
    List<Area> getAllArea(SearchTermDTO searchTermDTO);

    WareHouse save(WareHouse wareHouse);

    Page<WareHouse> loadDataAll(Pageable pageable, SearchTermDTO searchTermDTO);

    List<WarehouseDTO> loadWarehouse(SearchTermDTO searchTermDTO) throws JsonProcessingException;

    WarehouseDTO calculateFee(SearchTermDTO searchTermDTO) throws IOException;
}
