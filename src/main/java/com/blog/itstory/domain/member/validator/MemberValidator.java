package com.blog.itstory.domain.member.validator;

import com.blog.itstory.domain.member.repository.MemberRepository;
import com.blog.itstory.global.error.exception.AuthenticationException;
import com.blog.itstory.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberRepository memberRepository;

    public void validateRefreshTokenExpirationTime(LocalDateTime refreshTokenExpirationTime, LocalDateTime now){
        if (refreshTokenExpirationTime.isBefore(now)) {
            throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
    }
}
