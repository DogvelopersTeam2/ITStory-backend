package com.blog.itstory.api.login.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

public class TokenDto {


    @Getter
    @ApiModel(value = "로그인 요청 객체")
    public static class Request{

        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Email(message = "이메일 형식을 지켜줘")
        @ApiModelProperty(value = "이메일", required = true, example = "email@gmail.com")
        private String email;

        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Size(min = 4, max = 30, message = "비밀번호는 4~30자 사이로 해줘요")
        @ApiModelProperty(value = "비밀번호", required = true, example = "password1234")
        private String password;
    }

    @Getter @Builder
    @ApiModel(value = "로그인 요청 객체")
    public static class Response{

        @ApiModelProperty(value = "토큰 타입", required = true, example = "BEARER")
        private String grantType;

        @ApiModelProperty(value = "액세스 토큰", required = true, example = "{header}.{payload}.{signature}")
        private String accessToken;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @ApiModelProperty(value = "액세스 토큰 만료시간", required = true, example = "yyyy-MM-dd HH:mm:ss")
        private Date accessTokenExpirationTime;

        @ApiModelProperty(value = "리프레쉬 토큰", required = true, example = "{header}.{payload}.{signature}")
        private String refreshToken;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        @ApiModelProperty(value = "리프레쉬 토큰 만료시간", required = true, example = "yyyy-MM-dd HH:mm:ss")
        private Date refreshTokenExpirationTime;
    }
}

























