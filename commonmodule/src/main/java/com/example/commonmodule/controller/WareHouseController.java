package com.example.commonmodule.controller;

import com.example.commonmodule.dto.Result;
import com.example.commonmodule.dto.SearchTermDTO;
import com.example.commonmodule.dto.WarehouseDTO;
import com.example.commonmodule.entity.Area;
import com.example.commonmodule.entity.WareHouse;
import com.example.commonmodule.service.WareHouseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/warehouse")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WareHouseController {
    private final WareHouseService wareHouseService;

    public WareHouseController(WareHouseService wareHouseService) {
        this.wareHouseService = wareHouseService;
    }

    @GetMapping("/get-address")
    public ResponseEntity<List<Area>> getAllArea(@RequestParam String searchTerm) throws JsonProcessingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SearchTermDTO searchTermDTO = objectMapper.readValue(searchTerm, SearchTermDTO.class);
            List<Area> page = wareHouseService.getAllArea(searchTermDTO);
            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Result<WareHouse>> save(@RequestBody WareHouse wareHouse) {
        Result<WareHouse> result = new Result<>();
        wareHouse = wareHouseService.save(wareHouse);
        if (wareHouse == null) {
            result.setMessage("Tên kho đã tồn tại");
        }
        result.setBody(wareHouse);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Result<List<Object>>> loadDataAll(Pageable pageable, @RequestParam String searchTerm) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SearchTermDTO searchTermDTO = objectMapper.readValue(searchTerm, SearchTermDTO.class);
            Page<WareHouse> pageTust = wareHouseService.loadDataAll(pageable, searchTermDTO);
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

    @GetMapping("/load-warehouse")
    public ResponseEntity<List<WarehouseDTO>> loadWarehouse(@RequestParam String searchTerm) throws JsonProcessingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SearchTermDTO searchTermDTO = objectMapper.readValue(searchTerm, SearchTermDTO.class);
            List<WarehouseDTO> page = wareHouseService.loadWarehouse(searchTermDTO);
            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/calculate-fee")
    public ResponseEntity<WarehouseDTO> calculateFee(@RequestParam String searchTerm) throws IOException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SearchTermDTO searchTermDTO = objectMapper.readValue(searchTerm, SearchTermDTO.class);
            WarehouseDTO page = wareHouseService.calculateFee(searchTermDTO);
            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
