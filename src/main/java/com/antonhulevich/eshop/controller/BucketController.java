package com.antonhulevich.eshop.controller;

import com.antonhulevich.eshop.dto.BucketDto;
import com.antonhulevich.eshop.service.BucketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class BucketController {
    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @GetMapping("/bucket")
    public String aboutBucket(Model model, Principal principal){
        if(principal == null){
            model.addAttribute("bucket", new BucketDto());
        } else {
            BucketDto bucketDto = bucketService.getBucketDtoByUser(principal.getName());
            model.addAttribute("bucket", bucketDto);
        }
        return "bucket";
    }

    @PostMapping("/bucket/remove/{productId}")
    public String removeProductFromBucket(@PathVariable Long productId, Principal principal){
        bucketService.deleteProduct(principal.getName(), productId);
        return "redirect:/bucket";
    }

    @PostMapping("/bucket")
    public String commitBucket(Model model, Principal principal){
        if(principal != null){
            bucketService.commitBucketToOrder(principal.getName());
        }
        return "redirect:/bucket";
    }
}
