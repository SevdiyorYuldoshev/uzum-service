package com.example.uzumpr.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.uzumpr.dto.LoginDto;
import com.example.uzumpr.dto.ResponseDto;
import com.example.uzumpr.dto.UsersDto;
import com.example.uzumpr.model.Users;
import com.example.uzumpr.repository.UsersRepository;
import com.example.uzumpr.security.JwtService;
import com.example.uzumpr.service.mapper.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.uzumpr.service.validator.AppStatusCodes.*;
import static com.example.uzumpr.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    public ResponseDto<UsersDto> addUser(UsersDto dto) {
        Users users = userMapper.toEntity(dto);
        users.setIsActive((short) 1);
        usersRepository.save(users);

        return ResponseDto.<UsersDto>builder()
                .success(true)
                .data(userMapper.toDto(users))
                .message(OK)
                .build();
    }

    public ResponseDto<UsersDto> updateUser(UsersDto usersDto) {
        if (usersDto.getId() == null){
            return ResponseDto.<UsersDto>builder()
                    .message(NULL_VALUE)
                    .code(VALIDATION_ERROR_CODE)
                    .data(usersDto)
                    .build();
        }

        Optional<Users> userOptional = usersRepository.findById(usersDto.getId());
        Users user = userOptional.get();

        if (userOptional.isEmpty() || user.getIsActive() == 0 ){
            return ResponseDto.<UsersDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .data(usersDto)
                    .build();
        }
        if (usersDto.getGender() != null){
            user.setGender(usersDto.getGender());
        }
        if (usersDto.getEmail() != null){
            user.setEmail(usersDto.getEmail());
        }
        if (usersDto.getLastName() != null){
            user.setLastName(usersDto.getLastName());
        }
        //...
        try {
            usersRepository.save(user);

            return ResponseDto.<UsersDto>builder()
                    .data(userMapper.toDto(user))
                    .success(true)
                    .message(OK)
                    .build();
        }catch (Exception e){
            return ResponseDto.<UsersDto>builder()
                    .data(userMapper.toDto(user))
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    public ResponseDto<UsersDto> getUserByPhoneNumber(String phoneNumber) {
        return usersRepository.findFirstByPhoneNumber(phoneNumber)
                .map(u -> ResponseDto.<UsersDto>builder()
                        .data(userMapper.toDto(u))
                        .success(true)
                        .message(OK)
                        .build())
                .orElse(ResponseDto.<UsersDto>builder()
                        .message("User with phone number " + phoneNumber + " is not found")
                        .code(-1)
                        .build());
    }

    public ResponseDto<UsersDto> deleteUser(Integer id) {
        Optional<Users> optionalUser = usersRepository.findById(id);

        Users users = optionalUser.get();

        if(optionalUser.isEmpty() || users.getIsActive() == 0){
            return ResponseDto.<UsersDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .build();
        }

        users.setIsActive((short) 0);
        usersRepository.save(users);

        return ResponseDto.<UsersDto>builder()
                .message("User with ID " + id + " is deleted")
                .code(OK_CODE)
                .data(userMapper.toDto(users))
                .build();
    }

    public List<ResponseDto<UsersDto>> getAllUsers() {
        return usersRepository.findAll().stream()
                .map(u ->
                ResponseDto.<UsersDto>builder()
                        .data(userMapper.toDto(u))
                        .success(true)
                        .message(OK)
                        .build()).collect(Collectors.toList());
    }

    @Override
    public UsersDto loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> users = usersRepository.findFirstByEmail(username);
        if (users.isEmpty()) throw new UsernameNotFoundException("User with email" + username + " is not found");
        return userMapper.toDto(users.get());
    }

    public ResponseDto<String> loginUser(LoginDto loginDto) {
        UsersDto users = loadUserByUsername(loginDto.getUsername());
        if (!encoder.matches(loginDto.getPassword(),users.getPassword())){
            return ResponseDto.<String>builder()
                    .message("Password is not correct"+users.getPassword())
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }

        return ResponseDto.<String>builder()
                .success(true)
                .message(OK)
                .data(jwtService.generateToken(users))
                .build();
    }

    public ResponseDto<UsersDto> getById(Integer id) {
        return usersRepository.findById(id)
                .map(u -> ResponseDto.<UsersDto>builder()
                        .success(true)
                        .message(OK)
                        .data(userMapper.toDto(u))
                        .build())
                .orElse(ResponseDto.<UsersDto>builder()
                        .code(NOT_FOUND_CODE)
                        .message(NOT_FOUND)
                        .build());
    }
}