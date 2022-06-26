package com.example.ordermodule.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BillDTO {
    private Long id;
    private Double totalAmount;
    private Double fee;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String nameCli;
    private Long idCli;
    private String addressClient;
    private Long idWar;
    private String addressWarehouse;
    private Integer typeShip;
    private Integer idPol;
    private Integer status;
    private Long idWare;

    public BillDTO(Long id, Double totalAmount, LocalDate fromDate, String addressClient, String addressWarehouse, Integer typeShip, Integer status, Long idWare) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.fromDate = fromDate;
        this.addressClient = addressClient;
        this.addressWarehouse = addressWarehouse;
        this.typeShip = typeShip;
        this.status = status;
        this.idWare = idWare;
    }
}
