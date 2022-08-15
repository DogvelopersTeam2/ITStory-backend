package com.blog.itstory.domain.member.service;

import com.blog.itstory.domain.member.entity.Member;
import com.blog.itstory.domain.member.repository.MemberRepository;
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
}
