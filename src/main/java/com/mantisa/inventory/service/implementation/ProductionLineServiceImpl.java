package com.mantisa.inventory.service.implementation;

import com.mantisa.inventory.model.ProductionLineEntity;
import com.mantisa.inventory.model.ProductionLineTypeEntity;
import com.mantisa.inventory.repository.ProductionLineRepository;
import com.mantisa.inventory.repository.ProductionLineTypeRepository;
import com.mantisa.inventory.service.IProductionLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductionLineServiceImpl implements IProductionLineService {
    @Autowired
    private ProductionLineRepository productionLineRepository;
    @Autowired
    private ProductionLineTypeRepository productionLineTypeRepository;

    @Override
    public ProductionLineEntity save(ProductionLineEntity productionLineEntity) {
        return productionLineRepository.save(productionLineEntity);
    }

    @Override
    public List<ProductionLineEntity> getAll() {
        return productionLineRepository.findAll();
    }

    @Override
    public ProductionLineEntity getById(Long id) {
        return productionLineRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
       productionLineRepository.deleteById(id);
    }

    @Override
    public ProductionLineTypeEntity getProductionLineTypeById(Long id) {
        return productionLineTypeRepository.findById(id).orElse(null);
    }

    @Override
    public List<ProductionLineTypeEntity> getAllProductionLineTypes() {
        return productionLineTypeRepository.findAll();
    }
}
