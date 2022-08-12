package com.blog.itstory.domain.comment.repository;

import com.blog.itstory.domain.comment.entity.Comment;
import com.blog.itstory.domain.post.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post, Sort sort);

    void deleteAllByPost(Post post);
}
