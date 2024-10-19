package com.mantisa.inventory.repository;

import com.mantisa.inventory.model.ProductEntity;
import com.mantisa.inventory.model.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long> {

}
