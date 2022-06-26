package com.example.commonmodule.repository;

import com.example.commonmodule.dto.SearchTermDTO;
import com.example.commonmodule.dto.WarehouseDTO;
import com.example.commonmodule.entity.WareHouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WareHouseRepositoryCustom {
    Page<WareHouse> loadAllData(SearchTermDTO searchTermDTO, Pageable pageable);

    List<WarehouseDTO> loadWarehouseHaveProduct(SearchTermDTO searchTermDTO);
}
