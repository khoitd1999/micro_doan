package com.example.commonmodule.entity;

import com.example.commonmodule.dto.WarehouseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "warehouse", schema = "public")
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "WarehouseDTO",
                classes = {
                        @ConstructorResult(
                                targetClass = WarehouseDTO.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "address", type = String.class),
                                        @ColumnResult(name = "idProduct", type = Long.class),
                                }
                        )
                }
        ),
})
public class WareHouse implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_war")
    private String nameWar;

    @Column(name = "id_war")
    private String idWar;

    @Column(name = "id_dis")
    private String idDis;

    @Column(name = "id_pro")
    private String idPro;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "street")
    private String street;
}
