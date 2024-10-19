package com.mantisa.inventory.service.implementation;

import com.mantisa.inventory.model.ProductionLineEntity;
import com.mantisa.inventory.repository.ProductionLineRepository;
import com.mantisa.inventory.service.IProductionLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductionLineServiceImpl implements IProductionLineService {
    @Autowired
    private ProductionLineRepository productionLineRepository;

    @Override
    public ProductionLineEntity save(ProductionLineEntity productionLineEntity) {
        return null;
    }

    @Override
    public List<ProductionLineEntity> getAll() {
        return List.of();
    }

    @Override
    public ProductionLineEntity getById(Long id) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
