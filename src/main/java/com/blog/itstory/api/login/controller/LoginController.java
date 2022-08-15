package com.blog.itstory.api.login.controller;

import com.blog.itstory.api.login.dto.RegisterDto;
import com.blog.itstory.api.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

//    @PostMapping("/register")
//    public ResponseEntity register(@RequestBody @Valid RegisterDto requestDto){
//
//        loginService.register(requestDto);
//
//    }
}





















