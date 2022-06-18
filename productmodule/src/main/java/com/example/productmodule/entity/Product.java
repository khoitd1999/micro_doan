package com.example.productmodule.entity;

import com.example.productmodule.dto.ProductDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product", schema = "public")
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "ProductDTO",
                classes = {
                        @ConstructorResult(
                                targetClass = ProductDTO.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "namePro", type = String.class),
                                        @ColumnResult(name = "price", type = Double.class),
                                        @ColumnResult(name = "idCat", type = Long.class),
                                        @ColumnResult(name = "idBra", type = Long.class),
                                        @ColumnResult(name = "description", type = String.class),
                                        @ColumnResult(name = "screen", type = String.class),
                                        @ColumnResult(name = "os", type = String.class),
                                        @ColumnResult(name = "ram", type = String.class),
                                        @ColumnResult(name = "battery", type = String.class),
                                        @ColumnResult(name = "date", type = LocalDate.class),
                                        @ColumnResult(name = "image", type = byte[].class),
                                        @ColumnResult(name = "status", type = Boolean.class),
                                        @ColumnResult(name = "rate", type = Double.class),
                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name = "ProductDTOForReceipt",
                classes = {
                        @ConstructorResult(
                                targetClass = ProductDTO.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "namePro", type = String.class),
                                        @ColumnResult(name = "price", type = Double.class),
                                        @ColumnResult(name = "idCat", type = Long.class),
                                        @ColumnResult(name = "idBra", type = Long.class),
                                        @ColumnResult(name = "description", type = String.class),
                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name = "ProductDTOForWelcome",
                classes = {
                        @ConstructorResult(
                                targetClass = ProductDTO.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "namePro", type = String.class),
                                        @ColumnResult(name = "price", type = Double.class),
                                        @ColumnResult(name = "idCat", type = Long.class),
                                        @ColumnResult(name = "idBra", type = Long.class),
                                        @ColumnResult(name = "image", type = byte[].class),
                                }
                        )
                }
        ),
})
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_pro")
    private String namePro;

    @Column(name = "price")
    private Double price;

    @Column(name = "id_cat")
    private Long idCat;

    @Column(name = "id_bra")
    private Long idBra;

    @Column(name = "description")
    private String description;

    @Column(name = "screen")
    private String screen;

    @Column(name = "os")
    private String os;

    @Column(name = "ram")
    private String ram;

    @Column(name = "battery")
    private String battery;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "image")
    private byte[] image;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "rate")
    private Double rate;

    @Transient
    private Boolean isLoadMore;
}
