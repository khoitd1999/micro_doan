package com.example.commonmodule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "policys", schema = "public")
public class Policy implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "from_dis")
    private Integer fromDis;

    @Column(name = "to_dis")
    private Integer toDis;

    @Column(name = "amount")
    private Double amount;
}
