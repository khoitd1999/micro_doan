package com.example.usermodule.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchTermDTO {
    private String code;
    private String nameSearch;
    private String addressSearch;
    private Integer idCat;
    private Integer idBra;
    private Integer idEmp;
    private Integer idWar;
    private Integer sizeCurrent;
    private Integer priceFilter;
    private List<Long> listID;
    private String codeProvince;
    private String codeDistrict;
    private String codeWard;
    private Long idCli;
    private Long idBil;
    private Integer type;
    private Long idPro;
    private Boolean isAdmin;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fromDateSearch;
    private Integer statusSearch;
    private Integer typeShipSearch;
    private String usernameSearch;
    private String fullNameSearch;
}
