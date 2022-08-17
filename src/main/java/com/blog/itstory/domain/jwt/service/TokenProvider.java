package com.blog.itstory.domain.jwt.service;

import com.blog.itstory.api.login.dto.TokenDto;
import com.blog.itstory.domain.jwt.constant.GrantType;
import com.blog.itstory.domain.jwt.constant.TokenType;
import com.blog.itstory.domain.member.constant.Role;
import com.blog.itstory.global.error.exception.ErrorCode;
import com.blog.itstory.global.error.exception.NotValidTokenException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class TokenProvider {

    private String accessTokenExpirationTime = "900000";

    private String refreshTokenExpirationTime ="1210500000";

    private String tokenSecret = "NMA8JPctFuna59f5";

    public TokenDto.Response createTokenDto(String email, Role role){
        Date accessTokenExpireTime = createAccessTokenExpireTime();
        Date refreshTokenExpireTime = createRefreshTokenExpireTime();

        String accessToken = createAccessToken(email, role, accessTokenExpireTime);
        String refreshToken = createRefreshToken(email, refreshTokenExpireTime);

        return TokenDto.Response.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpirationTime(accessTokenExpireTime)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(refreshTokenExpireTime)
                .build();
    }

    public String createAccessToken(String email, Role role, Date expirationTime) {
        return Jwts.builder()
                .setSubject(TokenType.ACCESS.name())    // 토큰 제목 ACCESS
                .setAudience(email)                 //  토큰에 담을 개인정보(대상자)
                .setIssuedAt(new Date())        //  발급시간
                .setExpiration(expirationTime)  //
                .claim("role", role)    // key-value 로 값을 저장하는 claim 에 유저 역할인 ADMIN 을 넣음
                .signWith(SignatureAlgorithm.HS512, tokenSecret)    // 시그니처를 만들 알고리즘과 시크릿키 값
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    public String createRefreshToken(String email, Date expirationTime) {
        return Jwts.builder()
                .setSubject(TokenType.REFRESH.name())   //  토큰 제목 Refresh
                .setAudience(email)                 //  토큰에 담을 개인정보(대상자)
                .setIssuedAt(new Date())        //  발급시간
                .setExpiration(expirationTime)  //  refreshToken 만료 시간
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    public Date createAccessTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpirationTime));
    }

    public Date createRefreshTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpirationTime));
    }


    public Claims getTokenClaims(String accessToken) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(tokenSecret)
                    .parseClaimsJws(accessToken).getBody();
        } catch (Exception e){
            e.printStackTrace();
            throw new NotValidTokenException(ErrorCode.NOT_VALID_TOKEN);
        }
        return claims;
    }

    public boolean isTokenExpired(Date accessTokenExpiredTime) {
        Date now = new Date();
        if(now.after(accessTokenExpiredTime)){
            return true;
        }
        return false;
    }

    public String getMemberEmail(String accessToken) {
        String email;
        try {
            Claims claims = Jwts.parser().setSigningKey(tokenSecret)
                    .parseClaimsJws(accessToken).getBody();
            email = claims.getAudience();
        } catch (Exception e){
            e.printStackTrace();
            throw new NotValidTokenException(ErrorCode.NOT_VALID_TOKEN);
        }
        return email;
    }

    public boolean validateToken(String token) {
        try {
            // access Token 을 검증만 하는 것이기 때문에, getBody()로 Claim 을 가져와 무언가를 하진 않는다.
            Jwts.parser().setSigningKey(tokenSecret)
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e){   //  토큰 변조
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public String getTokenType(String token) {
        String tokenType;
        try {
            Claims claims = Jwts.parser().setSigningKey(tokenSecret)
                    .parseClaimsJws(token).getBody();
            tokenType = claims.getSubject();
        } catch (Exception e){
            e.printStackTrace();
            throw new NotValidTokenException(ErrorCode.NOT_VALID_TOKEN);
        }
        return tokenType;
    }
}

























