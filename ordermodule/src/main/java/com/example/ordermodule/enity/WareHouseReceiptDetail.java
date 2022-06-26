package com.example.ordermodule.enity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "warehousereceiptdetail", schema = "public")
public class WareHouseReceiptDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_ware")
    private Long idWare;

    @Column(name = "id_pro")
    private Long idPro;

    @Column(name = "name_pro")
    private String namePro;

    @Column(name = "price")
    private Double price;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "quantity")
    private Integer quantity;

    @Transient
    private Integer type;
}
