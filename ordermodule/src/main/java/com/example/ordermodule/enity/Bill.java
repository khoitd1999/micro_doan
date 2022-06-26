package com.example.ordermodule.enity;

import com.example.ordermodule.dto.BillDTO;
import com.example.ordermodule.dto.CartDTO;
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
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bill", schema = "public")
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "BillDTO",
                classes = {
                        @ConstructorResult(
                                targetClass = BillDTO.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "totalAmount", type = Double.class),
                                        @ColumnResult(name = "fromDate", type = LocalDate.class),
                                        @ColumnResult(name = "addressClient", type = String.class),
                                        @ColumnResult(name = "addressWarehouse", type = String.class),
                                        @ColumnResult(name = "typeShip", type = Integer.class),
                                        @ColumnResult(name = "status", type = Integer.class),
                                        @ColumnResult(name = "idWare", type = Long.class),
                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name = "CartDTO",
                classes = {
                        @ConstructorResult(
                                targetClass = CartDTO.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "idPro", type = Long.class),
                                        @ColumnResult(name = "namePro", type = String.class),
                                        @ColumnResult(name = "image", type = byte[].class),
                                        @ColumnResult(name = "quantity", type = Integer.class),
                                        @ColumnResult(name = "price", type = Double.class),
                                        @ColumnResult(name = "amount", type = Double.class),
                                }
                        )
                }
        ),
})
public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "fee")
    private Double fee;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "from_date")
    private LocalDate fromDate;

    @JsonSerialize(using = ToStringSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "name_cli")
    private String nameCli;

    @Column(name = "idcli")
    private Long idCli;

    @Column(name = "address_client")
    private String addressClient;

    @Column(name = "idwar")
    private Long idWar;

    @Column(name = "address_warehouse")
    private String addressWarehouse;

    @Column(name = "type_ship")
    private Integer typeShip;

    @Column(name = "id_pol")
    private Integer idPol;

    @Column(name = "status")
    private Integer status;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_bil")
    private List<Cart> carts = new ArrayList<>();
}
