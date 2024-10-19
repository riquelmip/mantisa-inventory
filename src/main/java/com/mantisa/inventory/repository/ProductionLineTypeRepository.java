package com.mantisa.inventory.repository;

import com.mantisa.inventory.model.ProductionLineEntity;
import com.mantisa.inventory.model.ProductionLineTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionLineTypeRepository extends JpaRepository<ProductionLineTypeEntity, Long> {
}
