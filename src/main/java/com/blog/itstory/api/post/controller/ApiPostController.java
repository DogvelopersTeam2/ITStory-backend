package com.blog.itstory.api.post.controller;

import com.blog.itstory.api.post.dto.GetPostDto;
import com.blog.itstory.api.post.dto.NewPostDto;
import com.blog.itstory.domain.post.constant.Category;
import com.blog.itstory.domain.post.entity.Post;
import com.blog.itstory.domain.post.repository.PostRepository;
import com.blog.itstory.domain.post.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class ApiPostController {

    private final PostService postService;

    @ApiOperation(value = "전체 글 조회")
    @GetMapping("/list")
    public ResponseEntity<List<GetPostDto>> getPosts(){

        // 일단 전체 리스트 받아오기
        List<Post> posts = postService.findAll();

        // 이제 DTO 로 변환해야 함. DTO 의 정적 팩토리 메소드 사용
        List<GetPostDto> postDtos = GetPostDto.from(posts);

        return ResponseEntity.ok(postDtos);
    }

    @ApiOperation(value = "글 작성")
    @PostMapping("/new")
    public ResponseEntity<NewPostDto.Response> newPost(@RequestBody NewPostDto.Request newPostRequestDto){

        // Request 정보를 활용, Post 객체 생성
        Post newPost = Post.builder()
                .postTitle(newPostRequestDto.getPostTitle())
                .postContent(newPostRequestDto.getPostContent())
                .category(Category.BACKEND) // 기본적으로 백엔드로 생성
                .build();

        // 저장
        Post savedPost = postService.save(newPost);

        // 반환 DTO 객체 생성
        NewPostDto.Response response = NewPostDto.Response.builder()
                .postId(savedPost.getPostId())
                .postTitle(savedPost.getPostTitle())
                .postContent(savedPost.getPostContent())
                .createTime(savedPost.getCreateTime().toLocalDate())
                .build();

        return ResponseEntity.ok(response);
    }
}
