package com.mantisa.inventory.repository;

import com.mantisa.inventory.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByCode(String code);

    @Query("SELECT MAX(p.productId) FROM ProductEntity p")
    Optional<Long> findLastProductId();

    @Query(value = """
                SELECT COUNT(*)
                FROM (
                    SELECT 1 
                    FROM production_orders po 
                    WHERE po.fk_requested_product_id = :productId
                    
                    UNION
                    
                    SELECT 1 
                    FROM production_order_details pod 
                    WHERE pod.fk_product_id = :productId
                ) AS result
            """, nativeQuery = true)
    Long countProductHasProductionOrders(@Param("productId") Long productId);

    @Query(value = """
                SELECT * FROM products p
                WHERE p.product_type = :type
            """, nativeQuery = true)
    List<ProductEntity> getAllByProductType(@Param("type") int type);


}
