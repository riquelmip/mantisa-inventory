package com.mantisa.inventory.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Builder
@Data
public class ProductionOrderDTO {
    private Long productionOrderId;
    private int orderNumber;
    private String customerName;
    private Date deliveryDate;
    private int quantity;
    private Long fkRequestedProductId;
    private String requestedProductName;
    private int status;
    private Set<OrderDetailDTO> orderDetails;
}