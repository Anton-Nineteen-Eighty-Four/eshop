package com.antonhulevich.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDto {
    private int amountProducts;
    private Double sum;
    private List<BucketDetailDto> bucketDetail = new ArrayList<>();

    public void aggregate(){
        this.amountProducts = bucketDetail.size();
        this.sum = bucketDetail.stream()
                .map(BucketDetailDto::getSum)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
