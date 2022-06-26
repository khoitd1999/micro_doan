package com.example.ordermodule.dto;

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
    private String nameWar;
    private String idWar;
    private String idDis;
    private String idPro;
    private String address;
    private Boolean status;
    private String street;
}
