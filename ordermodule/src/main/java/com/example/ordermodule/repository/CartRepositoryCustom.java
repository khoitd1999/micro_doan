package com.example.ordermodule.repository;

import com.example.ordermodule.dto.CartDTO;

import java.util.List;

public interface CartRepositoryCustom {
    List<CartDTO> getListCart(Long idCli);
}
