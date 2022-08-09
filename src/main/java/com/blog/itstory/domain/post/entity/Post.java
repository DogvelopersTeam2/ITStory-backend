package com.blog.itstory.domain.post.entity;

import com.blog.itstory.domain.comment.entity.Comment;
import com.blog.itstory.domain.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor
// 게시글 엔티티 클래스
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false, length = 100)
    private String postTitle;

    @Lob
    @Column(nullable = false)
    private String postContent;

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @Builder
    public Post(String postTitle, String postContent) {
        this.postTitle = postTitle;
        this.postContent = postContent;
    }
}
