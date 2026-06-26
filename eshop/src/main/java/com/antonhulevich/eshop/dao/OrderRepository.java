package com.antonhulevich.eshop.dao;

import com.antonhulevich.eshop.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
