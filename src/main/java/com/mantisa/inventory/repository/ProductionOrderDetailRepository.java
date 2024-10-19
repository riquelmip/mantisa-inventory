package com.mantisa.inventory.repository;

import com.mantisa.inventory.model.ProductionOrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionOrderDetailRepository extends JpaRepository<ProductionOrderDetailEntity, Long> {
}
