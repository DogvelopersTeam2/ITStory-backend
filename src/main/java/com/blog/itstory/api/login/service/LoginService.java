package com.blog.itstory.api.login.service;

import com.blog.itstory.api.login.dto.RegisterDto;
import com.blog.itstory.domain.member.entity.Member;
import com.blog.itstory.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterDto requestDto) {
        Member member = requestDto.toEntity(passwordEncoder);
        memberService.registerMember(member);
    }
}
