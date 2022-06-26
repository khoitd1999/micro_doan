package com.example.ordermodule.repository;

import com.example.ordermodule.enity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long>, InventoryRepositoryCustom {

    @Query(value = "select * from Inventory where id_war = ?1 ", nativeQuery = true)
    List<Inventory> getInventoriesByIdWar(Long idWar);

    @Modifying
    @Query(value = "delete from Inventory where id_war = ?1", nativeQuery = true)
    void removeInventoriesByIdWar(Long idWar);

    @Query(value = "select COALESCE(sum(quantity), 0) quantity from inventory i where id_war in (select id from warehouse w where w.id_pro = ?1)", nativeQuery = true)
    Integer getQuantityInventory(String code);
}
