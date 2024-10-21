package com.mantisa.inventory.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrdersReportDTO {
    private String order_number;
    private String customer_name;
    private String delivery_date;
    private String start_date;
    private String end_date;
    private String status;
}