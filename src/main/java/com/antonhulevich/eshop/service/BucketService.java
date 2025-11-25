package com.antonhulevich.eshop.service;

import com.antonhulevich.eshop.domain.Bucket;
import com.antonhulevich.eshop.domain.User;
import com.antonhulevich.eshop.dto.BucketDto;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> productIds);
    void addProducts(Bucket bucket, List<Long> productIds);
    BucketDto getBucketDtoByUser(String name);
    void deleteProduct(String name, Long productId);
}
