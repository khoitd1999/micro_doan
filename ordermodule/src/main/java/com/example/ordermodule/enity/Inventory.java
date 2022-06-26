package com.example.ordermodule.enity;

import com.example.ordermodule.dto.InventoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory", schema = "public")
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "InventoryDTO",
                classes = {
                        @ConstructorResult(
                                targetClass = InventoryDTO.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "idPro", type = Long.class),
                                        @ColumnResult(name = "namePro", type = String.class),
                                        @ColumnResult(name = "image", type = byte[].class),
                                        @ColumnResult(name = "idWar", type = Long.class),
                                        @ColumnResult(name = "nameWar", type = String.class),
                                        @ColumnResult(name = "quantity", type = Integer.class),
                                }
                        )
                }
        ),
})
public class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_pro")
    private Long idPro;

    @Column(name = "name_pro")
    private String namePro;

    @Column(name = "id_war")
    private Long idWar;

    @Column(name = "quantity")
    private Integer quantity;
}
