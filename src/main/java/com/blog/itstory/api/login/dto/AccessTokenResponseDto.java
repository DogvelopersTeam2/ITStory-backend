package com.blog.itstory.api.login.dto;

import com.blog.itstory.domain.jwt.constant.GrantType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Date;

@Builder
@Getter
@Setter
//  refresh 토큰을 이용한 access Token 재발급용 DTO
public class AccessTokenResponseDto {

    private String grantType;

    private String accessToken;

    // 날짜타입 변환은 JsonFormat 혹은 @DateTimeFormat (get 요청)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date accessTokenExpireTime;

    public static AccessTokenResponseDto of(String accessToken, Date accessTokenExpireTime){
        return AccessTokenResponseDto.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }
}

















