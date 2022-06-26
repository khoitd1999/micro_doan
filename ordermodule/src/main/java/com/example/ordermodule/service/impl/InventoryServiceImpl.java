package com.example.ordermodule.service.impl;

import com.example.ordermodule.dto.InventoryDTO;
import com.example.ordermodule.dto.SearchTermDTO;
import com.example.ordermodule.repository.InventoryRepository;
import com.example.ordermodule.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public Page<InventoryDTO> loadPagination(SearchTermDTO searchTermDTO, Pageable pageable) {
        return inventoryRepository.loadPagination(searchTermDTO, pageable);
    }

    @Override
    public Integer getQuantityInventory(SearchTermDTO searchTermDTO) {
        return inventoryRepository.getQuantityInventory(searchTermDTO.getCodeProvince());
    }
}
