package com.mantisa.inventory.service;


import com.mantisa.inventory.model.ProductionOrderEntity;
import com.mantisa.inventory.model.dto.OrdersReportDTO;

import java.util.List;

public interface IProductionOrderService {
    ProductionOrderEntity save(ProductionOrderEntity productionOrderEntity);

    List<ProductionOrderEntity> getAll();

    ProductionOrderEntity getById(Long id);

    void delete(Long id);

    int findLastOrderNumber();

    List<OrdersReportDTO> getOrdersReport(int status, String deliveryDate);

}
