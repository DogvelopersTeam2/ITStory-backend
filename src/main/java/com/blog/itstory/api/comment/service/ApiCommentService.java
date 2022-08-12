package com.blog.itstory.api.comment.service;

import com.blog.itstory.api.comment.dto.CommentDto;
import com.blog.itstory.domain.comment.entity.Comment;
import com.blog.itstory.domain.comment.repository.CommentRepository;
import com.blog.itstory.domain.comment.service.CommentService;
import com.blog.itstory.domain.post.entity.Post;
import com.blog.itstory.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApiCommentService {

    private final CommentService commentService;
    private final PostService postService;

    public CommentDto.Response addComment(CommentDto.Request requestDto, Long postId) {
        //  postId 이용, Post 객체 조회
        Post post = postService.findById(postId);

        //  post 필드를 가진 Comment 엔티티 생성
        Comment comment = requestDto.toEntity(post);

        // 저장
        Comment savedComment = commentService.save(comment);

        //  마지막으로, 엔티티를 DTO 로 변환
        return CommentDto.Response.of(savedComment);
    }

    public List<CommentDto.Response> findAllByPostId(Long postId) {
        List<Comment> comments = commentService.findAllByPostId(postId);
        return comments.stream()
                .map(comment -> CommentDto.Response.of(comment))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CommentDto.Response> updateComment(Long postId, Long commentId, CommentDto.Request requestDto) {
        // comment 객체를 가져와서 업데이트
        Comment comment = commentService.findById(commentId);

        // 반드시 postId(객체상에선 post 필드) 를 가지고 있을 것이기 때문에. Writer 와 Content 만 바꿈
        comment.update(requestDto.getCommentWriter(), requestDto.getCommentContent());

        //  다시 전체 호출. postId 는 이때 사용한다.
        //  그리고 comment 변수는 컨텍스트에 있는 상태로 업데이트되었으므로, 정상 조회 가능하다.
        List<Comment> comments = commentService.findAllByPostId(postId);

        //  stream.map 으로 DTO 변환 후 반환하는 ofList() 사용.
        return CommentDto.Response.ofList(comments);
    }

    @Transactional
    public List<CommentDto.Response> deleteComment(Long postId, Long commentId) {
        //  commentId 로 제거.
        commentService.deleteById(commentId);

        //  다시 전체 호출
        List<Comment> comments = commentService.findAllByPostId(postId);
        //  stream.map 으로 DTO 변환 후 반환하는 ofList() 사용.
        return CommentDto.Response.ofList(comments);
    }
}






















