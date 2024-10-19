package com.mantisa.inventory.repository;

import com.mantisa.inventory.model.ProductionOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionOrderRepository extends JpaRepository<ProductionOrderEntity, Long> {
    @Query(value = "SELECT COALESCE(MAX(order_number), 0) FROM production_orders", nativeQuery = true)
    int findLastOrderNumber();
}
