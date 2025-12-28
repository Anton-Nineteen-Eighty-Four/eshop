package com.antonhulevich.eshop.controller;

import com.antonhulevich.eshop.dto.ProductDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTest1 {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void chekGetProduct(){
        ResponseEntity<ProductDto> entity = restTemplate
                .withBasicAuth("admin", "admin")
                .getForEntity("/products/1", ProductDto.class);
        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

}