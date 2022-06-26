package com.example.ordermodule.repository.impl;

import com.example.ordermodule.dto.CartDTO;
import com.example.ordermodule.repository.CartRepositoryCustom;
import com.example.ordermodule.utils.Common;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartRepositoryImpl implements CartRepositoryCustom {
    @Autowired
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;


    @Override
    public List<CartDTO> getListCart(Long idCli) {
        StringBuilder sql = new StringBuilder();
        List<CartDTO> cartDTOS = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        sql.append(" from cart c join product p on c.id_pro = p.id where id_cli = :idCli and id_bil is null ");
        params.put("idCli", idCli);
        Query query = entityManager.createNativeQuery("Select c.id, c.id_pro idPro, c.name_pro namePro, p.image, c.quantity, c.price, c.amount " + sql.toString(), "CartDTO");
        Common.setParams(query, params);
        cartDTOS = query.getResultList();
        for (CartDTO cartDTO: cartDTOS) {
            if (cartDTO.getImage() != null && cartDTO.getImage().length > 0) {
                cartDTO.setImage(Common.decompressBytes(cartDTO.getImage()));
            }
            cartDTO.setIdCli(idCli);
        }
        return cartDTOS;
    }
}
