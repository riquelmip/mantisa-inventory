package com.mantisa.inventory.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
public class CreateOrdenDTO {
    String customer;
    Date deliveryDate;
    int fkRequestedProductId;
    int quantity;
    List<OrderDetailDTO> orderDetails;

}
