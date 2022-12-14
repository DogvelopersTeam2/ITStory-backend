package com.blog.itstory.api.comment.controller;

import com.blog.itstory.api.comment.dto.CommentDto;
import com.blog.itstory.api.comment.service.ApiCommentService;
import com.blog.itstory.domain.comment.service.CommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/{postId}/comment")
public class CommentController {

    private final ApiCommentService apiCommentService;

    @ApiOperation(value = "댓글 등록")
    @PostMapping
    public ResponseEntity<CommentDto.Response> addComment(@RequestBody @Validated CommentDto.Request requestDto
                                            , @PathVariable Long postId){
        /**
         *  바로 commentService 사용한다면?
         *  Dto.toEntity() 작업과
         *  Dto.of(Entity) 작업을 컨트롤러에서 수행해야 한다.

         *  하지만 ApiCommentService 를 사용하면
         *  Dto 관련 작업을 컨트롤러에서 보이지 않게 하고, 해당 로직을 ApiService에 숨길 수 있다.
         *  컨트롤러쪽 코드르 단순화하기 위해, Dto 관련 로직은 ApiCommentService가 해결하도록 하자. (PostController 코드도 수정해야 함)
         */
        //  댓글을 등록하고,  댓글 엔티티 를 응답 Dto로 바꾸는 작업
        CommentDto.Response responseDto = apiCommentService.addComment(requestDto, postId);

        //  리턴값 리스트로 바꾸기
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    //  테스트용 API. 댓글만 따로 조회하는 기능은 클라이언트 환경에서는 구현 예정 없다.
    @ApiOperation(value = "[테스트용] 게시글번호로 댓글만 따로 조회")
    @GetMapping
    public ResponseEntity<List<CommentDto.Response>> getComments(@PathVariable Long postId){

        List<CommentDto.Response> responseDto = apiCommentService.findAllByPostId(postId);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation(value = "댓글 수정")
    @PatchMapping("/{commentId}")
    public ResponseEntity<List<CommentDto.Response>> updateComment(@PathVariable Long postId, @PathVariable Long commentId
                                        , @RequestBody CommentDto.Request requestDto){

        List<CommentDto.Response> responseDto = apiCommentService.updateComment(postId, commentId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<List<CommentDto.Response>> deleteComment(@PathVariable Long postId, @PathVariable Long commentId){

        List<CommentDto.Response> responseDto = apiCommentService.deleteComment(postId, commentId);
        return ResponseEntity.ok(responseDto);
    }
}
































