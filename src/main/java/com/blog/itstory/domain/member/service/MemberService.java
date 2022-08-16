package com.blog.itstory.domain.member.service;

import com.blog.itstory.domain.member.entity.Member;
import com.blog.itstory.domain.member.repository.MemberRepository;
import com.blog.itstory.global.error.exception.EntityNotFoundException;
import com.blog.itstory.global.error.exception.ErrorCode;
import com.blog.itstory.global.error.exception.TokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public void registerMember(Member member) {
        memberRepository.save(member);
    }

    public Member findByEmail(String email) {
         return memberRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException(ErrorCode.EMAIL_NOT_EXIST));
    }

    public Member findByRefreshToken(String refreshToken) {
        return memberRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new TokenNotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
    }
}
