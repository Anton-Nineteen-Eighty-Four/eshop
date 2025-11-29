package com.antonhulevich.eshop.dto;

import java.util.ArrayList;
import java.util.List;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class BucketDto {

    private int amountProducts;
    private Double sum;
    private List<BucketDetailDto> bucketDetail = new ArrayList<>();

    public BucketDto() {
    }

    public BucketDto(int amountProducts, Double sum, List<BucketDetailDto> bucketDetail) {
        this.amountProducts = amountProducts;
        this.sum = sum;
        this.bucketDetail = bucketDetail;
    }

    public void aggregate(){
        this.amountProducts = bucketDetail.size();
        this.sum = bucketDetail.stream()
                .map(BucketDetailDto::getSum)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public int getAmountProducts() {
        return amountProducts;
    }

    public void setAmountProducts(int amountProducts) {
        this.amountProducts = amountProducts;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public List<BucketDetailDto> getBucketDetail() {
        return bucketDetail;
    }

    public void setBucketDetail(List<BucketDetailDto> bucketDetail) {
        this.bucketDetail = bucketDetail;
    }
}
