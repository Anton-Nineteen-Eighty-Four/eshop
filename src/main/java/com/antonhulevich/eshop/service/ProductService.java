package com.antonhulevich.eshop.service;

import com.antonhulevich.eshop.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAll();
}
