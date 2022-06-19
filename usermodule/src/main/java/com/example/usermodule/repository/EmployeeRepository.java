package com.example.usermodule.repository;

import com.example.usermodule.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom {

    @Query(value = "select * from Employee where role = ?1", nativeQuery = true)
    List<Employee> findAllByRole(String role);

    @Query(value = "select * from Employee where username = ?1 and password = ?2 and status = true ", nativeQuery = true)
    Employee checkLoginAdmin(String username, String password);

    @Query(value = "select * from Employee where username = ?1", nativeQuery = true)
    Employee findOneByUsername(String username);
}
