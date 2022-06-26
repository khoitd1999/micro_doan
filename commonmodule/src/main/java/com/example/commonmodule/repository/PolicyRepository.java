package com.example.commonmodule.repository;

import com.example.commonmodule.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
    @Query(value = "select * from Policys", nativeQuery = true)
    List<Policy> getAll();
}
