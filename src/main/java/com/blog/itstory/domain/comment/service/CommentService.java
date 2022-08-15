package com.blog.itstory.domain.comment.service;

import com.blog.itstory.domain.comment.entity.Comment;
import com.blog.itstory.domain.comment.repository.CommentRepository;
import com.blog.itstory.domain.post.entity.Post;
import com.blog.itstory.domain.post.repository.PostRepository;
import com.blog.itstory.global.error.exception.EntityNotFoundException;
import com.blog.itstory.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> findAllByPostId(Long postId) {
        // 해당 메소드를 호출하는 PostController 에서 이미 postId를 검증하였다.
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_EXISTS));
        return commentRepository.findAllByPost(post, Sort.by("commentId").descending());
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_EXISTS));
    }

    public void deleteById(Long commentId) {
        //  commentId로 자료를 조회 가능한지부터 확인
        commentRepository.findById(commentId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_EXISTS));

        //  자료 조회 가능함을 확인했으니, 삭제
        commentRepository.deleteById(commentId);
    }

    public void deleteAllByPost(Post post) {
        commentRepository.deleteAllByPost(post);
    }
}



















