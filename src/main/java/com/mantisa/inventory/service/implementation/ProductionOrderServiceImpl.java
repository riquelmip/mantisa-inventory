package com.mantisa.inventory.service.implementation;

import com.mantisa.inventory.model.ProductEntity;
import com.mantisa.inventory.model.ProductionOrderEntity;
import com.mantisa.inventory.repository.ProductRepository;
import com.mantisa.inventory.repository.ProductionOrderRepository;
import com.mantisa.inventory.service.IProductService;
import com.mantisa.inventory.service.IProductionOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductionOrderServiceImpl implements IProductionOrderService {
    @Autowired
    private ProductionOrderRepository productionOrderRepository;

    @Override
    public ProductionOrderEntity save(ProductionOrderEntity productionOrderEntity) {
        return productionOrderRepository.save(productionOrderEntity);
    }

    @Override
    public List<ProductionOrderEntity> getAll() {
        return productionOrderRepository.findAll();
    }

    @Override
    public ProductionOrderEntity getById(Long id) {
        return productionOrderRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        productionOrderRepository.deleteById(id);
    }

    @Override
    public int findLastOrderNumber() {
        return productionOrderRepository.findLastOrderNumber();
    }

}
