package com.mantisa.inventory.repository;

import com.mantisa.inventory.model.ProductionLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductionLineRepository extends JpaRepository<ProductionLineEntity, Long> {
}
