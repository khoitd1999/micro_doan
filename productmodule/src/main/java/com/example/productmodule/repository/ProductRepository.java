package com.example.productmodule.repository;

import com.example.productmodule.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    @Query(value = "select * from Product where name_pro = ?1", nativeQuery = true)
    Product findByName(String name);

    @Modifying
    @Query(value = "update Product " +
            "set name_pro = ?1, " +
            "price = ?2, " +
            "id_cat = ?3, " +
            "id_bra = ?4, " +
            "description = ?5, " +
            "screen = ?6, " +
            "os = ?7, " +
            "ram = ?8, " +
            "battery = ?9, " +
            "date = ?10, " +
            "status = ?11 where id = ?12", nativeQuery = true)
    void updateOne(String namePro, Double price, Long idCat, Long idBra, String description, String screen, String os, String ram, String battery, LocalDate date, Boolean status, Long id);

    @Query(value = "select rate from Comment where id_pro = ?1", nativeQuery = true)
    List<Double> listRate(Long id);

    @Modifying
    @Query(value = "update Product set rate = ?1 where id = ?2", nativeQuery = true)
    void updateRate(Double bd, Long id);
}
