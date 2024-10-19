package com.mantisa.inventory.service;


import com.mantisa.inventory.model.ProductionLineEntity;
import com.mantisa.inventory.model.ProductionLineTypeEntity;

import java.util.List;

public interface IProductionLineService {
    ProductionLineEntity save(ProductionLineEntity productionLineEntity);

    List<ProductionLineEntity> getAll();

    ProductionLineEntity getById(Long id);

    void delete(Long id);

    ProductionLineTypeEntity getProductionLineTypeById(Long id);

    List<ProductionLineTypeEntity> getAllProductionLineTypes();


}
