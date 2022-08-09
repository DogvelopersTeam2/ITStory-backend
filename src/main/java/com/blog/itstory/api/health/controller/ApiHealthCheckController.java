package com.blog.itstory.api.health.controller;

import com.blog.itstory.domain.member.constant.Role;
import com.blog.itstory.domain.member.entity.Member;
import com.blog.itstory.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiHealthCheckController {

    private final MemberRepository memberRepository;

    @GetMapping("/api/health/member1")
    public String healthCheck(){
        List<Member> members = memberRepository.findAll();
        return "1번 회원 조회, 회원 식별자" + members.get(0).getMemberId();
    }

    @GetMapping("/api/health/newMember")
    public String newMember(){
        Member member = Member.builder()
                .email("itstory@gmail.com")
                .password("password")
                .role(Role.GUEST)
                .build();
        Member savedMember = memberRepository.save(member);
        return "회원 생성. 회원 식별자: " + savedMember.getMemberId();
    }
}
