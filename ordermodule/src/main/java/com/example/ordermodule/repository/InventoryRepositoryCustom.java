package com.example.ordermodule.repository;

import com.example.ordermodule.dto.InventoryDTO;
import com.example.ordermodule.dto.SearchTermDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryRepositoryCustom {
    Page<InventoryDTO> loadPagination(SearchTermDTO searchTermDTO, Pageable pageable);
}
