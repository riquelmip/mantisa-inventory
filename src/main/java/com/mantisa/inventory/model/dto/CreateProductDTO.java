package com.mantisa.inventory.model.dto;

import lombok.Data;

@Data
public class CreateProductDTO {
    int productId;
    String description;
    String name;
    int productType;
    boolean status;
    int stock;
    int fkUnitId;
}
