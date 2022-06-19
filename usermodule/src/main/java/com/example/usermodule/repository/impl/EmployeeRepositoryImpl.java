package com.example.usermodule.repository.impl;

import com.example.usermodule.dto.SearchTermDTO;
import com.example.usermodule.entity.Employee;
import com.example.usermodule.repository.EmployeeRepositoryCustom;
import com.example.usermodule.utils.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {
    @Autowired
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public Page<Employee> loadAllData(SearchTermDTO searchTermDTO, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        List<Employee> employees = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        sql.append(" from employee  where 1 = 1 ");
        if (searchTermDTO.getUsernameSearch() != null && !searchTermDTO.getUsernameSearch().isEmpty()) {
            sql.append(" and username like :username ");
            params.put("username", "%" + searchTermDTO.getUsernameSearch() + "%");
        }
        if (searchTermDTO.getFullNameSearch() != null && !searchTermDTO.getFullNameSearch().isEmpty()) {
            sql.append(" and fullname like :fullname ");
            params.put("fullname", "%" + searchTermDTO.getFullNameSearch() + "%");
        }
        Query countQuery = entityManager.createNativeQuery("Select Count(1)" + sql.toString());
        Common.setParams(countQuery, params);
        Number total = (Number) countQuery.getSingleResult();
        if (total.longValue() > 0) {
            Query query = entityManager.createNativeQuery("select * " + sql.toString(), Employee.class);
            Common.setParamsWithPageable(query, params, pageable, total);
            employees = query.getResultList();
        }
        return new PageImpl<>(employees, pageable, total.longValue());
    }
}
