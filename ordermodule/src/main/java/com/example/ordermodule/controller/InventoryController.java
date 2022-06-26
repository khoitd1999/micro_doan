package com.example.ordermodule.controller;

import com.example.ordermodule.dto.InventoryDTO;
import com.example.ordermodule.dto.Result;
import com.example.ordermodule.dto.SearchTermDTO;
import com.example.ordermodule.service.InventoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/pagination")
    public ResponseEntity<Result<List<Object>>> loadDataAll(Pageable pageable, @RequestParam String searchTerm) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SearchTermDTO searchTermDTO = objectMapper.readValue(searchTerm, SearchTermDTO.class);
            Page<InventoryDTO> pageTust = inventoryService.loadPagination(searchTermDTO, pageable);
            Result<List<Object>> result = new Result<>();
            List<Object> list = new ArrayList<>();
            list.add(pageTust.getContent());
            list.add(pageTust.getTotalElements());
            result.setBody(list);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-quantity")
    public ResponseEntity<Integer> getQuantityInventory(@RequestParam String searchTerm) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SearchTermDTO searchTermDTO = objectMapper.readValue(searchTerm, SearchTermDTO.class);
            Integer pageTust = inventoryService.getQuantityInventory(searchTermDTO);
            return new ResponseEntity<>(pageTust, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
