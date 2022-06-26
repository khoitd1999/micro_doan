package com.example.commonmodule.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class WarehouseDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String idProvince;
    private String idDistrict;
    private String address;
    private Set<Long> available;
    private List<Long> unavailable;
    private Long idProduct;
    private String distance;
    private Double fee;
    private Integer idPol;

    public WarehouseDTO(Long id, String address, Long idProduct) {
        this.id = id;
        this.address = address;
        this.idProduct = idProduct;
    }
}
