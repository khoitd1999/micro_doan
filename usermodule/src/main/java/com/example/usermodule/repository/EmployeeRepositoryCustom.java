package com.example.usermodule.repository;

import com.example.usermodule.dto.SearchTermDTO;
import com.example.usermodule.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepositoryCustom {
    Page<Employee> loadAllData(SearchTermDTO searchTermDTO, Pageable pageable);
}
