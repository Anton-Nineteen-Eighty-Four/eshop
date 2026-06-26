package com.antonhulevich.eshop.controller;

import com.antonhulevich.eshop.dto.BucketDto;
import com.antonhulevich.eshop.service.BucketService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BucketController {
    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @GetMapping("/bucket")
    public String aboutBucket(Model model, @AuthenticationPrincipal UserDetails currentUser) {
        BucketDto bucketDto = bucketService.getBucketDtoByUser(currentUser != null ? currentUser.getUsername() : null);
        model.addAttribute("bucket", bucketDto);
        return "bucket";
    }

    @PostMapping("/bucket/remove/{productId}")
    public String removeProductFromBucket(@PathVariable Long productId,  @AuthenticationPrincipal UserDetails currentUser){
        bucketService.deleteProduct(currentUser.getUsername(), productId);
        return "redirect:/bucket";
    }

    @PostMapping("/bucket")
    public String commitBucket(@AuthenticationPrincipal UserDetails currentUser){
        if(currentUser != null){
            bucketService.commitBucketToOrder(currentUser.getUsername());
        }
        return "redirect:/bucket";
    }
}
