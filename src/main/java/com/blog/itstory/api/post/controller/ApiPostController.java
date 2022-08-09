package com.blog.itstory.api.post.controller;

import com.blog.itstory.api.post.dto.NewPostDto;
import com.blog.itstory.domain.post.entity.Post;
import com.blog.itstory.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiPostController {

    private final PostRepository postRepository;
    @PostMapping("/post/new")
    public ResponseEntity<NewPostDto.Response> newPost(@RequestBody NewPostDto.Request newPostRequestDto){

        Post newPost = Post.builder()
                .postTitle(newPostRequestDto.getPostTitle())
                .postContent(newPostRequestDto.getPostContent())
                .build();

        Post savedPost = postRepository.save(newPost);
        NewPostDto.Response response = new NewPostDto.Response(savedPost.getPostId());

        return ResponseEntity.ok(response);


    }
}
