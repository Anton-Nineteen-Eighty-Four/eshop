package com.antonhulevich.eshop.service;

import com.antonhulevich.eshop.dao.OrderRepository;
import com.antonhulevich.eshop.domain.Order;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

}