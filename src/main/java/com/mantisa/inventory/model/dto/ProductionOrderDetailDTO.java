package com.mantisa.inventory.model.dto;

import lombok.Data;

@Data
public class ProductionOrderDetailDTO {
    private Long id;
    private Long fkProductionOrderId;
    private Long fkProductId;
    private int quantity;
}