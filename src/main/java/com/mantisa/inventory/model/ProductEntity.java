package com.mantisa.inventory.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    // 1. Producto Terminado, 2. Materia Prima
    @Column(name = "product_type")
    private int productType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_unit_id")
    private UnitEntity unit;

    @Column(name = "stock")
    private int stock;

    @Column(name = "status")
    private boolean status;

}
