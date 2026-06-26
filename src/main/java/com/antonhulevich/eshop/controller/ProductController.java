package com.antonhulevich.eshop.controller;

import com.antonhulevich.eshop.dto.ProductDto;
import com.antonhulevich.eshop.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String list(Model model){
        List<ProductDto> productList = productService.getNonArchivedProducts();
        model.addAttribute("products", productList);
        return "products";
    }

    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id, @AuthenticationPrincipal UserDetails currentUser){
        if(currentUser == null){
            return "redirect:/login";
        }
        productService.addToUserBucket(id,currentUser.getUsername());
        return "redirect:/products";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.delete(id);
        return "redirect:/products";
    }
}
