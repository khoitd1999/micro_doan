package com.example.ordermodule.service.impl;

import com.example.ordermodule.dto.CartDTO;
import com.example.ordermodule.enity.Cart;
import com.example.ordermodule.repository.CartRepository;
import com.example.ordermodule.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart addCart(Cart cart) {
        Cart tmp = cartRepository.getCartNotOrderFollowClient(cart.getIdCli(), cart.getIdPro());
        if (tmp != null) {
            tmp.setQuantity(tmp.getQuantity() + cart.getQuantity());
            tmp.setAmount(tmp.getAmount() + cart.getAmount());
            cart = cartRepository.save(tmp);
        } else {
            cart = cartRepository.save(cart);
        }
        return cart;
    }

    @Override
    public Cart saveCart(Cart cart) {
        cart = cartRepository.save(cart);
        return cart;
    }

    @Override
    public void deleteCart(String id) {
        cartRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public List<CartDTO> getListCart(String idCli) {
        return cartRepository.getListCart(Long.parseLong(idCli));
    }
}
