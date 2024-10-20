package com.mantisa.inventory.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderDetailDTO {
    Long productId;
    int quantity;
}
