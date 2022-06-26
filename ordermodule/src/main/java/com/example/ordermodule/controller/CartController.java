package com.example.ordermodule.controller;

import com.example.ordermodule.dto.CartDTO;
import com.example.ordermodule.enity.Cart;
import com.example.ordermodule.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add-cart")
    public ResponseEntity<Cart> addCart(@RequestBody Cart cart) {
        try {
            cart = cartService.addCart(cart);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save-cart")
    public ResponseEntity<Cart> saveCart(@RequestBody Cart cart) {
        try {
            cart = cartService.saveCart(cart);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-cart")
    public ResponseEntity<Void> saveCart(@RequestParam String id) {
        try {
            cartService.deleteCart(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-cart-by-idCli")
    public ResponseEntity<List<CartDTO>> getListCart(@RequestParam String idCli) {
        try {
            List<CartDTO> carts = cartService.getListCart(idCli);
            return new ResponseEntity<>(carts, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
