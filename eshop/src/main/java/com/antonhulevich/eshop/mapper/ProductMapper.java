package com.antonhulevich.eshop.mapper;

import com.antonhulevich.eshop.domain.Product;
import com.antonhulevich.eshop.dto.ProductDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductDto productDto);

    @InheritInverseConfiguration
    ProductDto fromProduct(Product product);

    List<Product> toProductList(List<ProductDto> productDtoList);

    List<ProductDto> fromProductList(List<Product> productList);

}
