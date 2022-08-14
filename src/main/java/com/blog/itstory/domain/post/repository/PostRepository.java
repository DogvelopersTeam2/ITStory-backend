package com.blog.itstory.domain.post.repository;

import com.blog.itstory.domain.post.constant.Category;
import com.blog.itstory.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByCategory(Category category, Pageable pageable);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByPostContentIgnoreCaseContainingOrPostTitleContainingIgnoreCase(String postContent, String postTitle, Pageable pageable);

    //findAllWithPageable
}
