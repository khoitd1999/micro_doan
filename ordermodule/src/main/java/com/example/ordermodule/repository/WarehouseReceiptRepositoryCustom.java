package com.example.ordermodule.repository;

import com.example.ordermodule.dto.SearchTermDTO;
import com.example.ordermodule.dto.WarehouseReceiptDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WarehouseReceiptRepositoryCustom {
    Page<WarehouseReceiptDTO> loadPagination(SearchTermDTO searchTermDTO, Pageable pageable);

    Page<WarehouseReceiptDTO> loadDetailPagination(SearchTermDTO searchTermDTO, Pageable pageable);
}
