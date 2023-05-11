package com.example.uzumpr.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.uzumpr.dto.ProductDto;
import com.example.uzumpr.model.Product;

@Mapper(componentModel = "spring")
public abstract class ProductMapper implements CommonMapper<ProductDto, Product>{
    @Autowired
    protected CategoryMapper categoryMapper;
    @Mapping(target = "category", expression = "java(categoryMapper.toDto(product.getCategory()))")
    public abstract ProductDto toDto(Product product);

}
