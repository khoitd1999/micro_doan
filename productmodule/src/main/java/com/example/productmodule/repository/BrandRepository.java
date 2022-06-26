package com.example.productmodule.repository;

import com.example.productmodule.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    @Query(value = "select * from Brand where id_cat like ?1", nativeQuery = true)
    List<Brand> getBrand(String idCat);
}
