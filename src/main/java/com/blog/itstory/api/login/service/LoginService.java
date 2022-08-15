package com.blog.itstory.api.login.service;

import com.blog.itstory.api.login.dto.RegisterDto;
import com.blog.itstory.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberService memberService;

    public void register(RegisterDto requestDto) {

    }
}
