package com.antonhulevich.eshop.service;

import com.antonhulevich.eshop.dao.ProductRepository;
import com.antonhulevich.eshop.dto.ProductDto;
import com.antonhulevich.eshop.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductMapper mapper, ProductRepository productRepository) {
        this.mapper = mapper;
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> getAll() {
        return mapper.fromProductList(productRepository.findAll());
    }
}
