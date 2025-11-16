package com.antonhulevich.eshop.service;

import com.antonhulevich.eshop.dao.ProductRepository;
import com.antonhulevich.eshop.domain.Bucket;
import com.antonhulevich.eshop.domain.User;
import com.antonhulevich.eshop.dto.ProductDto;
import com.antonhulevich.eshop.mapper.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper mapper;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final BucketService bucketService;

    public ProductServiceImpl(ProductMapper mapper, ProductRepository productRepository, UserService userService, BucketService bucketService) {
        this.mapper = mapper;
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;
    }

    @Override
    public List<ProductDto> getAll() {
        return mapper.fromProductList(productRepository.findAll());
    }

    @Override
    public void addToUserBucket(Long productId, String username) {
        User user = userService.findByName(username);
        if(user == null){
            throw new RuntimeException("User " + username + " not fount");
        }
        Bucket bucket = user.getBucked();
        if (bucket == null) {
            Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
            user.setBucked(newBucket);
            userService.save(user);
        } else {
            bucketService.addProducts(bucket, Collections.singletonList(productId));
        }
    }
}
