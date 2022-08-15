package com.blog.itstory.api.login.controller;

import com.blog.itstory.api.login.dto.RegisterDto;
import com.blog.itstory.api.login.service.LoginService;
import com.blog.itstory.global.error.exception.BusinessException;
import com.blog.itstory.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody @Valid RegisterDto requestDto){

        if( ! requestDto.getPassword1().equals(requestDto.getPassword2())){
            throw new BusinessException(ErrorCode.MISMATCHED_PASSWORD);
        }
        loginService.register(requestDto);

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("status", "OK");

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
    }

    // todo 로그인 유지 구현
}





















