package com.example.usermodule.service.impl;

import com.example.usermodule.dto.SearchTermDTO;
import com.example.usermodule.entity.Employee;
import com.example.usermodule.repository.EmployeeRepository;
import com.example.usermodule.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee checkLoginAdmin(String username, String password) {
        Employee employee = employeeRepository.checkLoginAdmin(username, password);
        Employee tmp = new Employee();
        if (employee != null) {
            tmp.setId(employee.getId());
            tmp.setFullName(employee.getFullName());
            tmp.setRole(employee.getRole());
        }
        return tmp;
    }

    @Override
    public Page<Employee> loadDataAll(Pageable pageable, SearchTermDTO searchTermDTO) {
        return this.employeeRepository.loadAllData(searchTermDTO, pageable);
    }

    @Override
    public Employee save(Employee employee) {
        Employee check = employeeRepository.findOneByUsername(employee.getUsername());
        if (employee.getId() != null) {
            if (check != null && check.getId() != null && !check.getId().equals(employee.getId())) {
                return null;
            }
        } else {
            if (check != null && check.getId() != null) {
                return null;
            }
        }
        employee = employeeRepository.save(employee);
        return employee;
    }
}
