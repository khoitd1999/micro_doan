package com.example.ordermodule.enity;

import com.example.ordermodule.dto.WarehouseReceiptDTO;
import com.example.ordermodule.dto.WarehouseReceiptDetailDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "warehousereceipt", schema = "public")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "WarehouseReceiptDTO",
                classes = {
                        @ConstructorResult(
                                targetClass = WarehouseReceiptDTO.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "code", type = String.class),
                                        @ColumnResult(name = "nameEmp", type = String.class),
                                        @ColumnResult(name = "nameWar", type = String.class),
                                        @ColumnResult(name = "date", type = LocalDate.class),
                                        @ColumnResult(name = "totalAmount", type = Double.class),
                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name = "WarehouseReceiptDetailDTO",
                classes = {
                        @ConstructorResult(
                                targetClass = WarehouseReceiptDetailDTO.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "namePro", type = String.class),
                                        @ColumnResult(name = "quantity", type = Integer.class),
                                        @ColumnResult(name = "price", type = Double.class),
                                        @ColumnResult(name = "amount", type = Double.class),
                                }
                        )
                }
        ),
})
public class WareHouseReceipt implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "id_emp")
    private Long idEmp;

    @Column(name = "id_war")
    private Long idWar;

    @Column(name = "idbil")
    private Long idBil;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "fee")
    private Double fee;

    @Column(name = "type")
    private Integer type;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ware")
    private List<WareHouseReceiptDetail> wareHouseReceiptDetails = new ArrayList<>();
}
