package com.mantisa.inventory.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "production_orders")
public class ProductionOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "production_order_id")
    private Long productionOrderId;

    @Column(name = "order_number")
    private int orderNumber;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "delivery_date")
    private Date deliveryDate;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_requested_product_id")
    private ProductEntity requestedProduct;

    // Estado de la orden de producción, puede ser 0. "pendiente", 1. "en proceso" o 2. "terminada"
    @Column(name = "status")
    private int status;

     // Relación con la entidad intermedia ProductionOrderDetailEntity
    @OneToMany(mappedBy = "productionOrder", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ProductionOrderDetailEntity> orderDetails;


}
