package com.blog.itstory.domain.post.repository;

import com.blog.itstory.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
