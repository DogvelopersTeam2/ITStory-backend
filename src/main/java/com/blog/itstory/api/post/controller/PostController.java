package com.blog.itstory.api.post.controller;

import com.blog.itstory.api.post.dto.GetPostDto;
import com.blog.itstory.api.post.dto.NewPostDto;
import com.blog.itstory.api.post.dto.UpdatePostDto;
import com.blog.itstory.api.post.service.ApiPostService;
import com.blog.itstory.domain.post.constant.Category;
import com.blog.itstory.domain.post.entity.Post;
import com.blog.itstory.domain.post.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
// 컨트롤러는 필연적으로 Dto 에 의존하게 되므로,
// domain 계층에서 똑같이 PostController 이름을 하고 있는 클래스가 생길 걱정은 안 해도 될 듯.
// ApiPostController 가 아닌 PostController 로 하자.
public class PostController {

    private final PostService postService; // 도메인 자체와 관련된 일을 하는 postService
    private final ApiPostService apiPostService; // 특정 DTO와 관련된 일을 하는 PostService

    @ApiOperation(value = "전체 글 조회")
    @GetMapping("/list")
    public ResponseEntity<List<GetPostDto>> getPosts(@RequestParam(value = "category", required = false) Category category){

        List<Post> posts;
        if (category != null){
             posts = postService.findAllByCategory(category);
        } else {
             posts = postService.findAll();
        }
        // 일단 전체 리스트 받아오기

        // 이제 DTO 로 변환해야 함. DTO 의 정적 팩토리 메소드 사용
        List<GetPostDto> postDtos = GetPostDto.of(posts);

        return ResponseEntity.ok(postDtos);
    }

    @ApiOperation(value = "글 단건 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<GetPostDto> getSinglePost(@PathVariable Long postId){

        // 일단 전체 리스트 받아오기
        Post post = postService.findById(postId);

        // 이제 DTO 로 변환해야 함. DTO 의 정적 팩토리 메소드 사용
        GetPostDto postDto = GetPostDto.of(post);

        return ResponseEntity.ok(postDto);
    }



    @ApiOperation(value = "글 작성")
    @PostMapping
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

    @ApiOperation(value = "글 수정")
    @PatchMapping("/{postId}")
    public ResponseEntity<UpdatePostDto.Response> updatePost( @PathVariable(value = "postId") Long postId,
                                                @RequestBody UpdatePostDto.Request updatePostRequestDto){

        // 서비스에 DTO 데이터를 보내서 업데이트
        UpdatePostDto.Response updatePostResponseDto = apiPostService.updatePost(postId, updatePostRequestDto);

        // 수정 후 반환용으로, 단순 제목과 내용만 담긴 반환용 객체를 반환
        return ResponseEntity.ok(updatePostResponseDto);
    }

    // DTO 를 만들기 귀찮으며 데이터 형식이 하나로 고정일 경우, Map으로 받을 수 있다.
    @ApiOperation(value = "글 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId){

        postService.deleteById(postId);

        return ResponseEntity.ok().build();
    }
}




















