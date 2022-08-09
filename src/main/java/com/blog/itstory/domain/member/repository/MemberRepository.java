package com.blog.itstory.domain.member.repository;

import com.blog.itstory.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
