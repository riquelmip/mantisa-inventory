package com.mantisa.inventory.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_line_types")
public class ProductionLineTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_line_type_id")
    private Long productLineTypeId;

    @Column(name = "name")
    private String name;
}
