package com.example.commonmodule.repository.impl;

import com.example.commonmodule.dto.SearchTermDTO;
import com.example.commonmodule.dto.WarehouseDTO;
import com.example.commonmodule.entity.WareHouse;
import com.example.commonmodule.repository.WareHouseRepositoryCustom;
import com.example.commonmodule.utils.Common;
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

public class WareHouseRepositoryImpl implements WareHouseRepositoryCustom {
    @Autowired
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public Page<WareHouse> loadAllData(SearchTermDTO searchTermDTO, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        List<WareHouse> lst = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        sql.append(" From WareHouse where 1 = 1 ");
        if (searchTermDTO.getNameSearch() != null && !searchTermDTO.getNameSearch().trim().isEmpty()) {
            sql.append(" and name_war like :name");
            params.put("name", "%" + searchTermDTO.getNameSearch() + "%");
        }
        Query countQuerry = entityManager.createNativeQuery("SELECT Count(1) " + sql.toString());
        Common.setParams(countQuerry, params);
        Number total = (Number) countQuerry.getSingleResult();
        if (total.longValue() > 0) {
            Query query = entityManager.createNativeQuery("SELECT * " + sql.toString() + " ORDER BY name_war ", WareHouse.class);
            Common.setParamsWithPageable(query, params, pageable, total);
            lst = query.getResultList();
        }
        return new PageImpl<>(lst, pageable, total.longValue());
    }

    @Override
    public List<WarehouseDTO> loadWarehouseHaveProduct(SearchTermDTO searchTermDTO) {
        StringBuilder sql = new StringBuilder();
        List<WarehouseDTO> lst = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        sql.append(" select w.id, w.address, i.id_pro idProduct from warehouse w " +
                " join inventory i on w.id = i.id_war " +
                " where w.id_pro = :idProvince and i.id_pro in :listID and quantity > 0 ");
        params.put("idProvince", searchTermDTO.getCodeProvince());
        params.put("listID", searchTermDTO.getListID());
        Query query = entityManager.createNativeQuery(sql.toString(), "WarehouseDTO");
        Common.setParams(query, params);
        return query.getResultList();
    }
}
