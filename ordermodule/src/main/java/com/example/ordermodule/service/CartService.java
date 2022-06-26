package com.example.ordermodule.service;

import com.example.ordermodule.dto.CartDTO;
import com.example.ordermodule.enity.Cart;

import java.util.List;

public interface CartService {
    Cart addCart(Cart cart);

    Cart saveCart(Cart cart);

    void deleteCart(String id);

    List<CartDTO> getListCart(String idCli);
}
