package com.example.commonmodule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseReceiptDetailDTO {
    private Long id;
    private Long idWare;
    private Long idPro;
    private String namePro;
    private Double price;
    private Double amount;
    private Integer quantity;
    private Integer type;
}
