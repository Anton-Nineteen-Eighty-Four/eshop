package com.antonhulevich.eshop.service;

import com.antonhulevich.eshop.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAll();
    void addToUserBucket(Long productId, String username);
    void addProduct(ProductDto dto);
    ProductDto getById(Long id);
    void delete(Long id);
}
