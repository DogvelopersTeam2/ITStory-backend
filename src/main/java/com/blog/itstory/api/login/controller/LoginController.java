package com.blog.itstory.api.login.controller;

import com.blog.itstory.api.login.dto.AccessTokenResponseDto;
import com.blog.itstory.api.login.dto.RegisterDto;
import com.blog.itstory.api.login.dto.TokenDto;
import com.blog.itstory.api.login.service.LoginService;
import com.blog.itstory.api.login.validator.LoginValidator;
import com.blog.itstory.global.error.exception.BusinessException;
import com.blog.itstory.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final LoginValidator loginValidator;

    @PostMapping("/register")
    public ResponseEntity<Map<String, HttpStatus>> register(@RequestBody @Validated RegisterDto requestDto){

        if( ! requestDto.getPassword1().equals(requestDto.getPassword2())){
            throw new BusinessException(ErrorCode.MISMATCHED_PASSWORD);
        }
        loginService.register(requestDto);

        Map<String, HttpStatus> responseMap = new HashMap<>();
        responseMap.put("status", HttpStatus.CREATED);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto.Response> login(@RequestBody @Validated TokenDto.Request request){

        TokenDto.Response response = loginService.login(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity <Map<String, HttpStatus>> logout(HttpServletRequest httpServletRequest){

        // Interceptor 설정 제외했으므로, loginValidator 로 직접 검증함
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        loginValidator.validateAuthorization(authorizationHeader);

        String accessToken = authorizationHeader.split(" ")[1];
        loginService.logout(accessToken);

        Map<String, HttpStatus> responseMap = new HashMap<>();
        responseMap.put("status", HttpStatus.OK);

        return ResponseEntity.ok(responseMap);
    }

    // refresh token 이용, access token 재발급 요청 경로
    @PostMapping("/token")
    public ResponseEntity<AccessTokenResponseDto> getAccessToken(HttpServletRequest httpServletRequest){

        // Interceptor 설정 제외했으므로, loginValidator 로 직접 검증함
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        loginValidator.validateAuthorization(authorizationHeader);

        String refreshToken = authorizationHeader.split(" ")[1];
        AccessTokenResponseDto responseDto = loginService.createAccessTokenByRefreshToken(refreshToken, LocalDateTime.now());

        return ResponseEntity.ok(responseDto);
    }

}





















