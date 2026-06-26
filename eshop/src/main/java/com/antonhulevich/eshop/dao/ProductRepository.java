package com.antonhulevich.eshop.dao;

import com.antonhulevich.eshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Modifying
    @Query(value = "DELETE FROM buckets_products WHERE product_id = :productId", nativeQuery = true)
    void deleteFromBucketsProducts(@Param("productId") Long productId);
    List<Product> findAllByArchiveFalse();
}
