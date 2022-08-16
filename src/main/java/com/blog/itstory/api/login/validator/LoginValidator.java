package com.blog.itstory.api.login.validator;

import com.blog.itstory.domain.jwt.constant.GrantType;
import com.blog.itstory.global.error.exception.AuthenticationException;
import com.blog.itstory.global.error.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoginValidator {
    // 통과 실패 시 예외를 발생시키는 검증 메소드
    public void validateAuthorization(String authorizationHeader) {

        //  1. authorization 필수 체크. 헤더 부분에 Authorization 이 없으면 지정한 예외를 발생시킴
        //  토큰 유무 확인
        if(!StringUtils.hasText(authorizationHeader)){
            throw new AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        }

        //  2. authorization Bearer 체크
        String[] authorizations = authorizationHeader.split(" ");
        // GrantType.BEARER.getType() 은 "Bearer"문자열 반환
        if(authorizations.length < 2 || (!GrantType.BEARER.getType().equals(authorizations[0]))){
            throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
        }
    }

}
