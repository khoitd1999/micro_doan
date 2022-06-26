package com.example.ordermodule.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryDTO {
    private Long id;
    private Long idPro;
    private String namePro;
    private byte[] image;
    private Long idWar;
    private String nameWar;
    private Integer quantity;

    public InventoryDTO(Long id, Long idPro, String namePro, byte[] image, Long idWar, String nameWar, Integer quantity) {
        this.id = id;
        this.idPro = idPro;
        this.namePro = namePro;
        this.image = image;
        this.idWar = idWar;
        this.nameWar = nameWar;
        this.quantity = quantity;
    }
}
