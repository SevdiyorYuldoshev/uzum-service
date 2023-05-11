package com.example.uzumpr.service.validator;

import org.springframework.stereotype.Component;
import com.example.uzumpr.dto.ErrorDto;
import com.example.uzumpr.dto.ProductDto;
import com.example.uzumpr.dto.ResponseDto;

import java.util.ArrayList;
import java.util.List;

import static com.example.uzumpr.service.validator.AppStatusMessages.*;

@Component
public class ProductValidator {
    public List<ErrorDto> productValidator(ProductDto dto){
        List<ErrorDto> errorDtoList = new ArrayList<>();

        if(dto.getAmount() == null){
            errorDtoList.add(new ErrorDto("amount", NULL_VALUE));
        } else if (dto.getAmount() <= 0) {
            errorDtoList.add(new ErrorDto("amount", NEGATIVE_VALUE));
        }
        if(dto.getName() == null){
            errorDtoList.add(new ErrorDto("name", NULL_VALUE));
        } else if (dto.getName().length() == 0) {
            errorDtoList.add(new ErrorDto("name", EMPTY_STRING));
        }
        if(dto.getDescription() == null){
            errorDtoList.add(new ErrorDto("description", NULL_VALUE));
        } else if (dto.getDescription().length() == 0) {
            errorDtoList.add(new ErrorDto("description", EMPTY_STRING));
        }
        if(dto.getPrice() == null){
            errorDtoList.add(new ErrorDto("price", NULL_VALUE));
        } else if (dto.getPrice() <= 0) {
            errorDtoList.add(new ErrorDto("price", NEGATIVE_VALUE));
        }

        return errorDtoList;
    }
}
