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
@Table(name = "cart", schema = "public")
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_pro")
    private Long idPro;

    @Column(name = "name_pro")
    private String namePro;

    @Column(name = "id_bil")
    private Long idBil;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Double price;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "id_cli")
    private Long idCli;

}
