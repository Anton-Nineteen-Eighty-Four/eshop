package com.antonhulevich.eshop.dao;

import com.antonhulevich.eshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
