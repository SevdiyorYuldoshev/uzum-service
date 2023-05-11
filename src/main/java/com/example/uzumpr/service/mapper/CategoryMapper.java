package com.example.uzumpr.service.mapper;

import org.mapstruct.Mapper;
import com.example.uzumpr.dto.CategoryDto;
import com.example.uzumpr.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends CommonMapper<CategoryDto, Category> {

}
