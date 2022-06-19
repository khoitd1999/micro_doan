package com.example.usermodule.service;

import com.example.usermodule.dto.SearchTermDTO;
import com.example.usermodule.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    Employee checkLoginAdmin(String username, String password);

    Page<Employee> loadDataAll(Pageable pageable, SearchTermDTO searchTermDTO);

    Employee save(Employee employee);
}
