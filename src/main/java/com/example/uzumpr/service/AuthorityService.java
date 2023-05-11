package com.example.uzumpr.service;

import org.springframework.stereotype.Service;
import com.example.uzumpr.dto.ResponseDto;
import com.example.uzumpr.repository.AuthorityRepository;

import static com.example.uzumpr.service.validator.AppStatusCodes.VALIDATION_ERROR_CODE;
import static com.example.uzumpr.service.validator.AppStatusMessages.DUPLICATE_ERROR;
import static com.example.uzumpr.service.validator.AppStatusMessages.OK;

@Service
public record AuthorityService(AuthorityRepository authorityRepository) {
    public ResponseDto<Void> addAuthorityToUser(String username, String authorityName){
        if (authorityRepository.existsByUsernameAndAuthority(username, authorityName)){
            return ResponseDto.<Void>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message(DUPLICATE_ERROR)
                    .build();
        }
        return ResponseDto.<Void>builder()
                .success(true)
                .message(OK)
                .build();
    }
}
