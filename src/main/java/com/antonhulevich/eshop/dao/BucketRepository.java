package com.antonhulevich.eshop.dao;

import com.antonhulevich.eshop.domain.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepository extends JpaRepository<Bucket,Long> {
}
