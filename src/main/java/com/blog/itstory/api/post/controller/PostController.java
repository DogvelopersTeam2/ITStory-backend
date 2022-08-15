package com.blog.itstory.api.post.controller;

import com.blog.itstory.api.comment.dto.CommentDto;
import com.blog.itstory.api.comment.service.ApiCommentService;
import com.blog.itstory.api.post.dto.*;
import com.blog.itstory.api.post.service.ApiPostService;
import com.blog.itstory.domain.post.constant.Category;
import com.blog.itstory.domain.post.entity.Post;
import com.blog.itstory.domain.post.repository.PostRepository;
import com.blog.itstory.domain.post.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Validated
// 컨트롤러는 필연적으로 Dto 에 의존하게 되므로,
// domain 계층에서 똑같이 PostController 이름을 하고 있는 클래스가 생길 걱정은 안 해도 될 듯.
// ApiPostController 가 아닌 PostController 로 하자.
public class PostController {

    private final PostService postService; // 도메인 자체와 관련된 일을 하는 postService
    private final ApiPostService apiPostService; // 특정 DTO와 관련된 일을 하는 PostService
    private final ApiCommentService apiCommentService;
    private final PostRepository postRepository;

    private final int DEFAULT_PAGE_SIZE = 7;

    @ApiOperation(value = "전체 글 조회")
    @GetMapping("/list")
    @Transactional
    public ResponseEntity<List<GetPostDto>> getPosts(@RequestParam(required = false) Category category,
                                                     @RequestParam(required = false)   Optional<@Range(min = 1,message = "페이지는 1부터 검색해주세요")  Integer> page,
                                                     @RequestParam(required = false, defaultValue = "") String searchText){
        /**
         *  PageableDefault(size=5, page=0) 하는 방법도 있겠으나
         *  size 를 클라이언트 측에서 직접 변경하게 하고 싶지 않으므로 (오류 방지)
         *  page 만 Optional 로 받고, size 는 직접 넣어준다.
         */
        Pageable pageable = PageRequest.of(0, 2000, Sort.by("postId").descending());

        // 전체 리스트 받아오기
        List<GetPostDto> postDtos;

        if (category != null){
            // 카테고리가 있으면 searchText 무시함. Category=backend&searchText=spring 임의로 이렇게 보내도 무시된다는 것.
           postDtos = apiPostService.findAllByCategory(category, pageable);
        } else {
            //  카테고리가 없으면 검색어로 찾음.
            //  검색어는 기본적으로 empty고, empty 상태에선 where Post.category like "%%" 이렇게 나가서 모두 조회되는 듯.
            postDtos = apiPostService.findAll(searchText, searchText, pageable);
        }

        return ResponseEntity.ok(postDtos);
    }

    @ApiOperation(value = "전체 글 조회(페이징)")
    @GetMapping("/list/paging")
    // 페이징 기능 적용 후 이 메소드만 남기고, 기본 전체조회 메소드 삭제하기.
    public ResponseEntity<MainPageDto> getPostsWithPaging(@RequestParam(required = false) Category category,
                                                          @RequestParam(required = false) Optional<@Range(min = 1,message = "페이지는 1부터 검색해주세요") Integer> page,
                                                          @RequestParam(required = false, defaultValue = "") String searchText){
        /**
         *  PageableDefault(size=5, page=0) 하는 방법도 있겠으나
         *  size 를 클라이언트 측에서 직접 변경하게 하고 싶지 않으므로 (오류 방지)
         *  page 만 Optional 로 받고, size 는 직접 넣어준다.
         */
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get()-1 : 0, DEFAULT_PAGE_SIZE, Sort.by("postId").descending());

        // 전체 리스트 받아오기
        MainPageDto mainPageDto;
        if (category != null){
            mainPageDto = apiPostService.findAllByCategoryPage(category, pageable);
        } else {
            mainPageDto = apiPostService.findAllPage(searchText, searchText, pageable);
        }

        return ResponseEntity.ok(mainPageDto);
    }

    @ApiOperation(value = "글 단건 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<SinglePostDto> getSinglePost(@PathVariable Long postId){

        // 게시글 정보 받기
        GetPostDto postDto = apiPostService.findById(postId);

        //  게시글 소속 댓글 정보 받기. 댓글 정보를 불러오려면 일단 글의 존재를 확인해야 한다.
        //  그런데 첫줄에서 postId를 이미 한번 검증했는데, //  처리 로직 실행 중 DB 변경을 고려해서 검증을 또 하는 것이 맞을까?
        //  만약 두 번째 검증을 생략했다가, 첫 번째 검증을 하고 돌아오는 사이에 글이 삭제되었으면 어떡하지?
        //  이러한 DB 동시성 문제는 어떻게 해결할까? 모든 메소드에 검증로직을 달아야 하나? 아니면 다른 기술적 해결방법이 있을까?
        //  스레드는 사용자마다 스프링이 알아서 생성해주니까 메모리상의 값은 그렇다 쳐도, DB는 모든 사용자가 공유하는데?
        //  JPA 책 뒷부분을 일단 읽어보자. 비관적 락과 낙관적 락, 동시성 문제 등으로 찾아보자.
        //  일단 findAllByPostId 는 다른 컨트롤러 메소드에서도 호출되므로, 검증로직을 넣기로 함.
        List<CommentDto.Response> comments = apiCommentService.findAllByPostId(postId);

        SinglePostDto singlePostDto = SinglePostDto.builder()
                .post(postDto)
                .comments(comments)
                .build();

        return ResponseEntity.ok(singlePostDto);
    }

    @ApiOperation(value = "글 작성")
    @PostMapping
    public ResponseEntity<NewPostDto.Response> addPost(@RequestBody @Validated NewPostDto.Request requestDto){

        NewPostDto.Response response = apiPostService.save(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @ApiOperation(value = "글 수정")
    @PatchMapping("/{postId}")
    public ResponseEntity<UpdatePostDto.Response> updatePost( @PathVariable(value = "postId") Long postId,
                                                @RequestBody @Validated UpdatePostDto.Request updatePostRequestDto){

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




















