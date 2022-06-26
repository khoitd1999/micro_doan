package com.example.ordermodule.service;

import com.example.ordermodule.dto.SearchTermDTO;
import com.example.ordermodule.dto.WarehouseReceiptDTO;
import com.example.ordermodule.enity.WareHouseReceipt;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WareHouseReceiptService {
    List<Object> getEmployeeAndWareHouse() throws JsonProcessingException;

    WareHouseReceipt save(WareHouseReceipt wareHouseReceipt);

    WareHouseReceipt createExport(WareHouseReceipt wareHouseReceipt);

    Page<WarehouseReceiptDTO> loadPagination(SearchTermDTO searchTermDTO, Pageable pageable);

    WareHouseReceipt findOne(Long id);

    Page<WarehouseReceiptDTO> loadDetailPagination(SearchTermDTO searchTermDTO, Pageable pageable);
}
