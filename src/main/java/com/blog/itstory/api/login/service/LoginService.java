package com.blog.itstory.api.login.service;

import com.blog.itstory.api.login.dto.AccessTokenResponseDto;
import com.blog.itstory.api.login.dto.RegisterDto;
import com.blog.itstory.api.login.dto.TokenDto;
import com.blog.itstory.domain.jwt.constant.TokenType;
import com.blog.itstory.domain.jwt.service.TokenProvider;
import com.blog.itstory.domain.member.constant.Role;
import com.blog.itstory.domain.member.entity.Member;
import com.blog.itstory.domain.member.service.MemberService;
import com.blog.itstory.domain.member.validator.MemberValidator;
import com.blog.itstory.global.error.exception.AuthenticationException;
import com.blog.itstory.global.error.exception.BusinessException;
import com.blog.itstory.global.error.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final MemberValidator memberValidator;

    public void register(RegisterDto requestDto) {
        Member member = requestDto.toEntity(passwordEncoder);
        memberService.registerMember(member);
    }

    @Transactional
    public TokenDto.Response login(TokenDto.Request request) {

        //  비밀번호 일치 검사
        Member member = memberService.findByEmail(request.getEmail());
        if (! passwordEncoder.matches(request.getPassword(), member.getPassword())){
            throw new BusinessException(ErrorCode.PASSWORD_NOT_MATCHES);
        }

        TokenDto.Response tokenDto = tokenProvider.createTokenDto(member.getEmail(), Role.ADMIN);
        // 토큰을 생성했으니, member 객체에 refreshToken 관련 정보 업데이트!
        member.updateRefreshTokenAndExpirationTime(tokenDto);
        return tokenDto;
    }

    @Transactional // Member 객체의 tokenExpirationTime 을 set 해서 변경시키기 위함.
    public void logout(String accessToken) {
        //  1. access Token 만료 확인
        Claims tokenClaims = tokenProvider.getTokenClaims(accessToken);
        Date accessTokenExpiration = tokenClaims.getExpiration();
        if( tokenProvider.isTokenExpired(accessTokenExpiration)){
            throw new AuthenticationException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        }

        //  2. 토큰 타입 검증
        String tokenType = tokenClaims.getSubject();
        if (!TokenType.isAccessToken(tokenType)){
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }

        //  3. 로그인할 때 발행한 refresh token 만료 처리
        String email = tokenProvider.getMemberEmail(accessToken); // claims.getAudience() 로 email 가져옴
        Member member = memberService.findByEmail(email);
        member.expireRefreshToken(LocalDateTime.now()); // Member 객체의 필드 tokenExpirationTime 만료시킴. 변경 감지
    }

    public AccessTokenResponseDto createAccessTokenByRefreshToken(String refreshToken, LocalDateTime now) {
        // Member 객체를 찾아온 후
        Member member = memberService.findByRefreshToken(refreshToken);
        //  토큰 검증. 만료됐다면 예외 발생.
        memberValidator.validateRefreshTokenExpirationTime(member.getTokenExpirationTime(), now);

        //  만료 시간을 새롭게 생성 후 액세스 토큰 생성.
        Date accessTokenExpireTime = tokenProvider.createAccessTokenExpireTime();// new Date() + 15분으로 만료시간 생성
        String accessToken = tokenProvider.createAccessToken(member.getEmail(), member.getRole(), accessTokenExpireTime);

        return AccessTokenResponseDto.of(accessToken, accessTokenExpireTime);
    }
}

















