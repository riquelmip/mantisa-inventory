package com.mantisa.inventory.service;


import com.mantisa.inventory.model.ProductEntity;
import com.mantisa.inventory.model.dto.CreateProductDTO;

import java.util.List;

public interface IProductService {
    ProductEntity save(ProductEntity productEntity);

    List<ProductEntity> getAll();

    ProductEntity getById(Long id);

    void delete(Long id);

    ProductEntity getByCode(String code);

    Long findLastProductId();

    boolean productHasProductionOrders(Long productId);

 List<ProductEntity> saveAll(List<ProductEntity> productEntities);

}
