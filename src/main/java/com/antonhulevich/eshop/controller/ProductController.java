package com.antonhulevich.eshop.controller;

import com.antonhulevich.eshop.domain.Product;
import com.antonhulevich.eshop.dto.ProductDto;
import com.antonhulevich.eshop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    private final ProductService productService;

    @GetMapping
    public String list(Model model){
        List<ProductDto> productList = productService.getAll();
        model.addAttribute("products", productList);
        return "products";
    }

    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id, Principal principal){
        if(principal == null){
            return "redirect:/products";
        }
        productService.addToUserBucket(id,principal.getName());
        return "redirect:/products";
    }

    @PostMapping("/addProduct")
    public String addProduct(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "redirect:/products";
    }
}
