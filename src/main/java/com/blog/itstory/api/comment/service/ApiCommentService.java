package com.blog.itstory.api.comment.service;

import com.blog.itstory.api.comment.dto.CommentDto;
import com.blog.itstory.domain.comment.entity.Comment;
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

//        System.out.println("저장 전에도 ID가 나오나? = "+comment.getCommentId()); // 영속 상태가 아니므로 null 출력됨!
//        System.out.println("저장 전에도 createTime?  = "+comment.getCreateTime());

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
}






















