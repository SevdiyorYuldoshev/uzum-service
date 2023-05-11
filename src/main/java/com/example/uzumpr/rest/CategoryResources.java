package com.example.uzumpr.rest;

import com.example.uzumpr.dto.CategoryDto;
import com.example.uzumpr.dto.ResponseDto;
import com.example.uzumpr.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("category")
public class CategoryResources {
    private final CategoryService categoryService;
    @PostMapping
    public ResponseDto<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }
    @GetMapping("/{id}")
    public ResponseDto<List<CategoryDto>> getById(@PathVariable Integer id){
        return categoryService.findById(id);
    }
}
