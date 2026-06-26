package com.antonhulevich.eshop.controller;

import com.antonhulevich.eshop.dto.ProductDto;
import com.antonhulevich.eshop.service.ProductService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ProductWebSocketController {
    private final ProductService productService;

    public ProductWebSocketController(ProductService productService) {
        this.productService = productService;
    }

    @MessageMapping("/products")
    public void messageAddProduct(ProductDto dto){
        productService.addProduct(dto);
    }

}
