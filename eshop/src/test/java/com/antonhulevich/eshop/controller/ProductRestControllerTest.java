package com.antonhulevich.eshop.controller;

import com.antonhulevich.eshop.dto.ProductDto;
import com.antonhulevich.eshop.service.ProductService;
import com.antonhulevich.eshop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductRestController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;
    @MockitoBean
    private UserService userService;

    private ProductDto dto = new ProductDto(999L, "TestProduct", new BigDecimal(999.99), false);

    @BeforeEach
    void setUp() {
        given(productService.getById(dto.getId())).willReturn(dto);
    }

    @Test
    void getById() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/products/{id}", dto.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(999))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("TestProduct"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(999.99));
    }
}