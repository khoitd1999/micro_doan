package com.example.ordermodule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseReceiptDetailDTO {
    private Long id;
    private String namePro;
    private Integer quantity;
    private Double price;
    private Double amount;
}
