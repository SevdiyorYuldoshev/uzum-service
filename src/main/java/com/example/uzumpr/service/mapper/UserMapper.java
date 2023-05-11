package com.example.uzumpr.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.uzumpr.dto.UsersDto;
import com.example.uzumpr.model.Users;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements CommonMapper<UsersDto, Users>{
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    @Mapping(target = "enabled", expression = "java(true)")
    public abstract Users toEntity(UsersDto dto);
}
