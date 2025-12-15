package com.antonhulevich.eshop.service;

import com.antonhulevich.eshop.domain.Order;

public interface OrderService {
    void saveOrder(Order order);
}