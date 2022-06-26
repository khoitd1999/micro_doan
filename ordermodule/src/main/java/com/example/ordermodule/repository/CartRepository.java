package com.example.ordermodule.repository;

import com.example.ordermodule.enity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom {
    @Query(value = "select * from Cart where id_cli = ?1 and id_bil is null and id_pro = ?2", nativeQuery = true)
    Cart getCartNotOrderFollowClient(Long idCli, Long idPro);

    @Modifying
    @Query(value = "update Cart set id_bil = ?1 where id in ?2", nativeQuery = true)
    void updateCart(Long idBil, List<Long> ids);
}
