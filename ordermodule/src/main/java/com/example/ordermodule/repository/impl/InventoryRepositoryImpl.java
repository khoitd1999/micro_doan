package com.example.ordermodule.repository.impl;

import com.example.ordermodule.dto.InventoryDTO;
import com.example.ordermodule.dto.SearchTermDTO;
import com.example.ordermodule.repository.InventoryRepositoryCustom;
import com.example.ordermodule.utils.Common;
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

public class InventoryRepositoryImpl implements InventoryRepositoryCustom {
    @Autowired
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Override
    public Page<InventoryDTO> loadPagination(SearchTermDTO searchTermDTO, Pageable pageable) {
        StringBuilder sql = new StringBuilder();
        List<InventoryDTO> inventoryDTOS = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        sql.append(" from inventory i " +
                "    join product p on i.id_pro = p.id " +
                "    join warehouse w on i.id_war = w.id where 1 = 1 ");
        if (searchTermDTO.getIdPro() != null) {
            sql.append(" and i.id_pro = :idPro ");
            params.put("idPro", searchTermDTO.getIdPro());
        }
        if (searchTermDTO.getIdWar() != null) {
            sql.append(" and i.id_war = :idWar ");
            params.put("idWar", searchTermDTO.getIdWar());
        }
        Query countQuery = entityManager.createNativeQuery("Select Count(1)" + sql.toString());
        Common.setParams(countQuery, params);
        Number total = (Number) countQuery.getSingleResult();
        if (total.longValue() > 0) {
            Query query = entityManager.createNativeQuery("select i.id, i.id_pro idPro, i.name_pro namePro, p.image, i.id_war idWar, w.name_war nameWar, i.quantity " + sql.toString(), "InventoryDTO");
            Common.setParamsWithPageable(query, params, pageable, total);
            inventoryDTOS = query.getResultList();
            for (InventoryDTO item: inventoryDTOS) {
                if (item.getImage() != null && item.getImage().length > 0) {
                    item.setImage(Common.decompressBytes(item.getImage()));
                }
            }
        }
        return new PageImpl<>(inventoryDTOS, pageable, total.longValue());
    }

}
