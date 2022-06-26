package com.example.ordermodule.service;

import com.example.ordermodule.dto.InventoryDTO;
import com.example.ordermodule.dto.SearchTermDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService {
    Page<InventoryDTO> loadPagination(SearchTermDTO searchTermDTO, Pageable pageable);

    Integer getQuantityInventory(SearchTermDTO searchTermDTO);
}
