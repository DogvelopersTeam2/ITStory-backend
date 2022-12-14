package com.blog.itstory.global.interceptor;

import com.blog.itstory.domain.jwt.constant.GrantType;
import com.blog.itstory.domain.jwt.constant.TokenType;
import com.blog.itstory.domain.jwt.service.TokenProvider;
import com.blog.itstory.global.error.exception.AuthenticationException;
import com.blog.itstory.global.error.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.pattern.PathPattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("AuthenticationInterceptor : preHandle");
        if (true) {
            return true;
        }
        if( request.getMethod().equals( "GET" )){
            return true;
        }

        //  댓글 작성 /post/66/comment [POST] 요청을 허용하기 위한 로직
        String[] splitedUri = request.getRequestURI().split("/");
        // "" + post + 66 + comment 로 분리되므로, 배열 길이가 4이며 [POST] 요청일 시 true
        if( request.getMethod().equals("POST") && splitedUri.length == 4){
            //  배열 길이(uri 형식) 과 [POST] 요청이 맞으므로, 요청한 uri 가 댓글작성 uri 인지 마지막으로 확인
            if ( (splitedUri[1]+splitedUri[3]).equals("postcomment")){
                log.info("댓글 작성을 허용합니다");
                return true;
            }
        }

        log.info("인증 진입");


        //  1. authorization 헤더가 있는지 체크
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(authorizationHeader)){
            throw new AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        }

        //  2. authorization Header의 TokenType Bearer 체크
        String[] authorizations = authorizationHeader.split(" ");
        if( authorizations.length < 2 || ( !GrantType.BEARER.getType().equals(authorizations[0]))){
            throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
        }

        //  3. 토큰 검증
        String token = authorizations[1];   // token 변수는 액세스 토큰의 몸통 부분.
        if( !tokenProvider.validateToken(token)){
            throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
        }

        //  4. 토큰 타입 검증. ACCESS or REFRESH
        String tokenType = tokenProvider.getTokenType(token);
        if( !TokenType.ACCESS.name().equals(tokenType)){
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }

        //  5. 액세스 토큰 만료 시작 검증

        // 일단 isTokenExpired() 인자로 넣을 토큰만료일 Date 객체를 가져옴
        Claims tokenClaims = tokenProvider.getTokenClaims(token);
        Date expiration = tokenClaims.getExpiration();

        if (tokenProvider.isTokenExpired(expiration)) {
            throw new AuthenticationException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("AuthenticationInterceptor : postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("AuthenticationInterceptor : afterCompletion");
    }
}
