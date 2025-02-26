package com.mantisa.inventory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "production_order_details")
public class ProductionOrderDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "production_order_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_production_order_id", nullable = false)
    @JsonIgnore
    private ProductionOrderEntity productionOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_product_id", nullable = false)
    @JsonIgnore
    private ProductEntity product;

    @Column(name = "quantity")
    private int quantity;
}
