package com.example.ordermodule.repository;

import com.example.ordermodule.enity.WareHouseReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WarehouseReceiptRepository extends JpaRepository<WareHouseReceipt, Long>, WarehouseReceiptRepositoryCustom {

    @Query(value = "select * from WareHouseReceipt where code = ?1", nativeQuery = true)
    WareHouseReceipt findByCode(String code);

    @Query(value = "select w.* from warehousereceipt w join warehousereceiptdetail wd on w.id = wd.id_ware " +
            " where w.id_war in ?1 and wd.id_pro in ?2 ", nativeQuery = true)
    List<WareHouseReceipt> loadWarehouseReceipt(List<Long> idWarehouse, List<Long> idPro);

    @Query(value = "select * from warehousereceipt where id_war = ?1 ", nativeQuery = true)
    List<WareHouseReceipt> loadWarehouseReceiptByIdWar(Long idWar);
}
