package com.example.commonmodule.controller;

import com.example.commonmodule.entity.Policy;
import com.example.commonmodule.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/policy")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @GetMapping("/load-all")
    public ResponseEntity<List<Policy>> loadDataAll() {
        // b1
        try {
            List<Policy> list = policyService.loadAllData();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
