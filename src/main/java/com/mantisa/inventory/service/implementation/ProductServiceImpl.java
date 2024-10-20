package com.mantisa.inventory.service.implementation;

import com.mantisa.inventory.model.ProductEntity;
import com.mantisa.inventory.model.UnitEntity;
import com.mantisa.inventory.model.dto.CreateProductDTO;
import com.mantisa.inventory.repository.ProductRepository;
import com.mantisa.inventory.repository.UnitRepository;
import com.mantisa.inventory.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Override
    public ProductEntity save(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    @Override
    public List<ProductEntity> getAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity getById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductEntity getByCode(String code) {
        return productRepository.findByCode(code).orElse(null);
    }

    @Override
    public Long findLastProductId() {
        return productRepository.findLastProductId().orElse(0L);
    }

    @Override
    public boolean productHasProductionOrders(Long productId) {
        return productRepository.countProductHasProductionOrders(productId) > 0;
    }

   public List<ProductEntity> saveAll(List<ProductEntity> productEntityList) {
    List<ProductEntity> s = productRepository.saveAll(productEntityList);
    return s;
}

    @Override
    public List<UnitEntity> getAllUnits() {
        return unitRepository.findAll();
    }

    @Override
    public List<ProductEntity> getAllByProductType(int productType) {
        return productRepository.getAllByProductType(productType);
    }


}
