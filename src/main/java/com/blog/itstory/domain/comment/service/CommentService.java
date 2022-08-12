package com.blog.itstory.domain.comment.service;

import com.blog.itstory.domain.comment.entity.Comment;
import com.blog.itstory.domain.comment.repository.CommentRepository;
import com.blog.itstory.domain.post.entity.Post;
import com.blog.itstory.domain.post.repository.PostRepository;
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
        Post post = postRepository.findById(postId).get();
        return commentRepository.findAllByPost(post, Sort.by("commentId").descending());
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).get();
    }

    public void deleteById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public void deleteAllByPost(Post post) {
        commentRepository.deleteAllByPost(post);
    }
}



















