package com.mantisa.inventory.repository;

import com.mantisa.inventory.model.ProductionOrderEntity;
import com.mantisa.inventory.model.dto.OrdersReportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductionOrderRepository extends JpaRepository<ProductionOrderEntity, Long> {
    @Query(value = "SELECT COALESCE(MAX(order_number), 0) FROM production_orders", nativeQuery = true)
    int findLastOrderNumber();




}
