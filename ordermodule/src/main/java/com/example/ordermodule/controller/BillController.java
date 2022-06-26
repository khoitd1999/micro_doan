package com.example.ordermodule.controller;

import com.example.ordermodule.dto.BillDTO;
import com.example.ordermodule.dto.CartDTO;
import com.example.ordermodule.dto.Result;
import com.example.ordermodule.dto.SearchTermDTO;
import com.example.ordermodule.enity.Bill;
import com.example.ordermodule.service.BillService;
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
@RequestMapping("/bill")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/save")
    public ResponseEntity<Result<Bill>> save(@RequestBody Bill bill) {
        Result<Bill> result = new Result<>();
        result.setBody(billService.save(bill));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/cancel-order")
    public ResponseEntity<Void> cancelOrder(@RequestBody Bill bill) {
        billService.cancelOrder(bill);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Result<List<Object>>> loadDataAll(Pageable pageable, @RequestParam String searchTerm) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SearchTermDTO searchTermDTO = objectMapper.readValue(searchTerm, SearchTermDTO.class);
            Page<BillDTO> pageTust = billService.loadPagination(searchTermDTO, pageable);
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

    @GetMapping("/pagination-detail")
    public ResponseEntity<Result<List<Object>>> loadDataAllDetail(Pageable pageable, @RequestParam String searchTerm) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SearchTermDTO searchTermDTO = objectMapper.readValue(searchTerm, SearchTermDTO.class);
            Page<CartDTO> pageTust = billService.loadDetailPagination(searchTermDTO, pageable);
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

    @GetMapping("/{id}")
    public ResponseEntity<Bill> find(@PathVariable Long id) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Bill bill = billService.findOne(id);
            return new ResponseEntity<>(bill, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
