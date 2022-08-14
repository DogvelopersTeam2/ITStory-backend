package com.blog.itstory.api.post.service;

import com.blog.itstory.api.post.dto.GetPostDto;
import com.blog.itstory.api.post.dto.MainPageDto;
import com.blog.itstory.api.post.dto.NewPostDto;
import com.blog.itstory.api.post.dto.UpdatePostDto;
import com.blog.itstory.domain.post.constant.Category;
import com.blog.itstory.domain.post.entity.Post;
import com.blog.itstory.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<GetPostDto> findAllByCategory(Category category, Pageable pageable) {
        Page<Post> posts = postService.findAllByCategory(category, pageable);
        // 이제 DTO 로 변환해야 함. DTO 의 정적 팩토리 메소드 사용
        return GetPostDto.ofList(posts.getContent());
    }
    // 페이징 기능 적용 후 findAllByCategory 메소드 제거 후, 해당 메소드 사용.
    public MainPageDto findAllByCategoryPage(Category category, Pageable pageable) {

        Page<Post> posts = postService.findAllByCategory(category, pageable);

        // 이제 DTO 로 변환해야 함. DTO 의 정적 팩토리 메소드 사용
        List<GetPostDto> postDtos = GetPostDto.ofList(posts.getContent());

        //  마지막으로 DTO 에 클라이언트가 활용할 정보를 넣음
        return MainPageDto.builder()
                .currentPage(posts.getNumber() + 1)
                .defaultSizeofPage(posts.getSize())
                .totalPages(posts.getTotalPages())
                .totalPostCount(posts.getTotalElements())
                .isFirstPage(posts.isFirst())
                .isLastPage(posts.isLast())
                .postDtos(postDtos)
                .build();
    }

    public List<GetPostDto> findAll(Pageable pageable) {
        Page<Post> posts = postService.findAll(pageable);

        // 이제 DTO 로 변환해야 함. DTO 의 정적 팩토리 메소드 사용
        return GetPostDto.ofList(posts.getContent());
    }



    public MainPageDto findAllPage(Pageable pageable) {
        Page<Post> posts = postService.findAll(pageable);

        // 이제 DTO 로 변환해야 함. DTO 의 정적 팩토리 메소드 사용
        List<GetPostDto> postDtos = GetPostDto.ofList(posts.getContent());

        //  마지막으로 DTO 에 클라이언트가 활용할 정보를 넣음
        return MainPageDto.builder()
                .currentPage(posts.getNumber() + 1)
                .defaultSizeofPage(posts.getSize())
                .totalPages(posts.getTotalPages())
                .totalPostCount(posts.getTotalElements())
                .isFirstPage(posts.isFirst())
                .isLastPage(posts.isLast())
                .postDtos(postDtos)
                .build();
    }

    public GetPostDto findById(Long postId) {
        Post post = postService.findById(postId);
        // 이제 DTO 로 변환해야 함. DTO 의 정적 팩토리 메소드 사용
        return GetPostDto.of(post);
    }

    public NewPostDto.Response save(NewPostDto.Request requestDto) {
        // Request 정보를 활용, Post 객체 생성
        Post newPost = Post.builder()
                .postTitle(requestDto.getPostTitle())
                .postContent(requestDto.getPostContent())
                .category(requestDto.getPostCategory()) // 기본적으로 백엔드로 생성
                .build();

        // 저장
        Post savedPost = postService.save(newPost);

        // 반환 DTO 객체 생성
        return NewPostDto.Response.builder()
                .postId(savedPost.getPostId())
                .postTitle(savedPost.getPostTitle())
                .postContent(savedPost.getPostContent())
                .postCategory(savedPost.getCategory())
                .createTime(savedPost.getCreateTime().toLocalDate())
                .build();
    }

    public List<GetPostDto> findAll(String postContent, String postTitle, Pageable pageable) {
        Page<Post> foundPost = postService.findAll(postContent, postTitle, pageable);
        return GetPostDto.ofList(foundPost.getContent());
    }
}























