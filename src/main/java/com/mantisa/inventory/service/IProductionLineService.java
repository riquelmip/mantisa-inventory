package com.mantisa.inventory.service;


import com.mantisa.inventory.model.ProductionLineEntity;

import java.util.List;

public interface IProductionLineService {
    ProductionLineEntity save(ProductionLineEntity productionLineEntity);

    List<ProductionLineEntity> getAll();

    ProductionLineEntity getById(Long id);

    boolean delete(Long id);


}
