package com.antonhulevich.eshop.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BucketDto {
    private int amountProducts;
    private BigDecimal sum = BigDecimal.ZERO;
    private List<BucketDetailDto> bucketDetail = new ArrayList<>();

    public BucketDto() {
    }

    public BucketDto(int amountProducts, BigDecimal sum, List<BucketDetailDto> bucketDetail) {
        this.amountProducts = amountProducts;
        this.sum = sum;
        this.bucketDetail = bucketDetail;
    }

    public void aggregate() {
        this.amountProducts = bucketDetail.size();
        this.sum = bucketDetail.stream()
                .map(BucketDetailDto::getSum)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getAmountProducts() {
        return amountProducts;
    }

    public void setAmountProducts(int amountProducts) {
        this.amountProducts = amountProducts;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public List<BucketDetailDto> getBucketDetail() {
        return bucketDetail;
    }

    public void setBucketDetail(List<BucketDetailDto> bucketDetail) {
        this.bucketDetail = bucketDetail;
    }
}
