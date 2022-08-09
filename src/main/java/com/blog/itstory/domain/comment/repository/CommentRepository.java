package com.blog.itstory.domain.comment.repository;

import com.blog.itstory.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
