package com.blog.itstory.api.post.service;

import com.blog.itstory.api.post.dto.UpdatePostDto;
import com.blog.itstory.domain.post.entity.Post;
import com.blog.itstory.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
// 이 클래스는 특정 Dto 와 관련된 역할을 하므로, Dto 변환작업(toEntity 혹은 해당 객체 값 추출)만 하고 나머지는
// PostService 에 맡기자!
public class ApiPostService {

    private final PostService postService;

    @Transactional // 수정 위함
    public UpdatePostDto.Response updatePost(Long postId, UpdatePostDto.Request updatePostRequestDto) {

        // 1. toEntity 실행. updatePost는 컨텍스트에 올라가지 않는 Entity로, 사실상의 Dto 역할을 하게 된다.
        Post updatePost = updatePostRequestDto.toEntity();

        /**
         *  postService.updatePost()의 파라미터로, Dto.getter()로 추출한 값을 모두 넣어줄 수도 있겠지만,
         *  파라미터 수가 너무 많아져서, 사실상의 DTO 역할을 하는 updatePost 변수를 파라미터로 보낸다.
         *  updatePost 변수는 현재 Id값이 없고, Category 정보도 없다. 그저 postTitle, postContent 를 실어나르기 위한 사실상의 Dto
         */
        //  2. 저장
        Post savedPost = postService.updatePost(postId, updatePost);

        //  3. 저장 후 반환 엔티티를 이용, Dto 생성 후 반환
        return UpdatePostDto.Response.of(savedPost);

    }
}



















