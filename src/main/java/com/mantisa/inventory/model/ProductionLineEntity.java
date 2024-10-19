package com.mantisa.inventory.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "production_lines")
public class ProductionLineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "production_line_id")
    private Long productionLineId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_production_line_type_id")
    private ProductionLineTypeEntity productionLineType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_production_order_id")
    private ProductionOrderEntity productionOrder;

    // Estado de la orden de producción, puede ser 0. "asignada", 2. "en proceso" o 3. "terminada"
    @Column(name = "status")
    private int status;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;


}
