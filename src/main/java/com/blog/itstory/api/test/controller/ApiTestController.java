package com.blog.itstory.api.test.controller;

import com.blog.itstory.api.test.dto.ApiTestDto;
import com.blog.itstory.domain.member.constant.Role;
import com.blog.itstory.domain.member.entity.Member;
import com.blog.itstory.domain.member.repository.MemberRepository;
import com.blog.itstory.domain.post.constant.Category;
import com.blog.itstory.domain.post.entity.Post;
import com.blog.itstory.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class ApiTestController {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @GetMapping("/member1")
    public String healthCheck(){
        List<Member> members = memberRepository.findAll();
        return "1번 회원 조회, 회원 식별자" + members.get(0).getMemberId();
    }

    @GetMapping("/newMember")
    public String newMember(){
        Member member = Member.builder()
                .email("itstory@gmail.com")
                .password("password")
                .role(Role.GUEST)
                .build();
        Member savedMember = memberRepository.save(member);
        return "회원 생성. 회원 식별자: " + savedMember.getMemberId();
    }

    @GetMapping
    public ResponseEntity<ApiTestDto> test(){
        ArrayList<ApiTestDto.TestCommentDto> commentDtos = new ArrayList<>();
        ApiTestDto.TestCommentDto comment1 = ApiTestDto.TestCommentDto.builder()
                .commentWriter("오혜성")
                .commentContent("오혜성의 댓글")
                .build();

        ApiTestDto.TestCommentDto comment2 = ApiTestDto.TestCommentDto.builder()
                .commentWriter("주동석")
                .commentContent("주동석의 댓글")
                .build();

        commentDtos.add(comment1); commentDtos.add(comment2);

        ApiTestDto testDto = ApiTestDto.builder()
                .postContent("게시글내용내용내용내용내용내용내용내용내용내용내용내용")
                .postTitle("게시글제목1")
                .postComments(commentDtos)
                .build();

        return ResponseEntity.ok(testDto);
    }

    //@GetMapping("/dummy")
    public String createDummyData(){
        Post post1 = Post.builder()
                .postTitle("백엔드 게시글 제목")
                .postContent("백엔드 게시글 내용")
                .category(Category.BACKEND)
                .build();

        Post post2 = Post.builder()
                .postTitle("IOS 게시글 제목")
                .postContent("IOS 게시글 내용")
                .category(Category.IOS)
                .build();

        Post post3 = Post.builder()
                .postTitle("프론트엔드 게시글 제목")
                .postContent("프론트엔드 게시글 내용")
                .category(Category.FRONTEND)
                .build();

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        return "더미데이터 저장 완료";
    }


}


























