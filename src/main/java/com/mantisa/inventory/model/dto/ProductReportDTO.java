package com.mantisa.inventory.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.StringReader;
import java.util.Date;
import java.util.Set;

@Builder
@Data
public class ProductReportDTO {
    private String code;
    private String name;
    private String description;
    private String productType;
    private String unitName;
    private int stock;
    private String status;
}