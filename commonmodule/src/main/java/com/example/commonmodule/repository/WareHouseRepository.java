package com.example.commonmodule.repository;

import com.example.commonmodule.entity.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WareHouseRepository extends JpaRepository<WareHouse, Long>, WareHouseRepositoryCustom {
    @Query(value = "select * from WareHouse where name_war = ?1", nativeQuery = true)
    WareHouse findByName(String name);

    @Query(value = "select * from WareHouse where id_pro = ?1", nativeQuery = true)
    List<WareHouse> findWarehouseByIdPro(String idPro);

    @Query(value = "select * from WareHouse where id_pro = ?1 and id_dis = ?2", nativeQuery = true)
    List<WareHouse> findWarehouseByIdProAndIdDis(String idPro, String idDis);
}
