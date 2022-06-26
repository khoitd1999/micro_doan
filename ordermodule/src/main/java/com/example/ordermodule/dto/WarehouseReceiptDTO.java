package com.example.ordermodule.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class WarehouseReceiptDTO {
    private Long id;
    private String code;
    private String nameEmp;
    private String nameWar;
    private LocalDate date;
    private Double totalAmount;
    private Long idBil;

    public WarehouseReceiptDTO(Long id, String code, String nameEmp, String nameWar, LocalDate date, Double totalAmount) {
        this.id = id;
        this.code = code;
        this.nameEmp = nameEmp;
        this.nameWar = nameWar;
        this.date = date;
        this.totalAmount = totalAmount;
    }
}
