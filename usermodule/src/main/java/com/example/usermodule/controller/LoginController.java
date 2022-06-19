package com.example.usermodule.controller;

import com.example.usermodule.dto.Result;
import com.example.usermodule.dto.SearchTermDTO;
import com.example.usermodule.entity.Client;
import com.example.usermodule.entity.Employee;
import com.example.usermodule.service.ClientService;
import com.example.usermodule.service.EmployeeService;
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
@RequestMapping("/login")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {
    private final EmployeeService employeeService;

    @Autowired
    private ClientService clientService;

    public LoginController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/check-login-admin")
    public ResponseEntity<Result<Employee>> checkLoginAdmin(@RequestParam String username, @RequestParam String password) {
        Result<Employee> result = new Result<>();
        Employee employee = employeeService.checkLoginAdmin(username, password);
        if (employee.getId() == null) {
            result.setMessage("Tài khoản mật khẩu không tồn tại");
        }
        result.setBody(employee);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/pagination-admin")
    public ResponseEntity<Result<List<Object>>> loadDataAll(Pageable pageable, @RequestParam String searchTerm) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            SearchTermDTO searchTermDTO = objectMapper.readValue(searchTerm, SearchTermDTO.class);
            Page<Employee> pageTust = employeeService.loadDataAll(pageable, searchTermDTO);
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

    @PostMapping("/check-login")
    public ResponseEntity<Result<Client>> checkLogin(@RequestParam String phone, @RequestParam String password) {
        Result<Client> result = new Result<>();
        Client client = clientService.checkLogin(phone, password);
        if (client.getId() == null) {
            result.setMessage("Tài khoản mật khẩu không tồn tại");
        }
        result.setBody(client);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Result<Client>> register(@RequestBody Client client) {
        Result<Client> result = new Result<>();
        client = clientService.save(client);
        if (client == null) {
            result.setMessage("Số điện thoại đã được đăng ký");
        }
        result.setBody(client);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/save-user-admin")
    public ResponseEntity<Result<Employee>> register(@RequestBody Employee employee) {
        Result<Employee> result = new Result<>();
        employee = employeeService.save(employee);
        if (employee == null) {
            result.setMessage("Tài khoản đã tồn tại");
        }
        result.setBody(employee);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
