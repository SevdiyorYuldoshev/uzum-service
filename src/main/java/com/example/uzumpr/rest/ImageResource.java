package com.example.uzumpr.rest;

import com.example.uzumpr.dto.ResponseDto;
import com.example.uzumpr.model.Image;
import com.example.uzumpr.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("image")
@RequiredArgsConstructor
public class ImageResource {

    private final ImageService imageService;

    @PostMapping
    public ResponseDto<Image> uploadImage(@RequestBody MultipartFile image){
        return imageService.saveImage(image);
    }
}
