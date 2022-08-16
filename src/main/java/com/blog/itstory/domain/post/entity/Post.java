package com.blog.itstory.domain.post.entity;

import com.blog.itstory.domain.comment.entity.Comment;
import com.blog.itstory.domain.common.BaseEntity;
import com.blog.itstory.domain.post.constant.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.List;
import java.util.TimeZone;

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

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private Category category;

    //  글에 속한 댓글. 글을 조회할 때 댓글도 같이 보여줘야 하기 때문에 다대일 양방향 매핑함.
    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Comment> comments;

    @Builder
    public Post(String postTitle, String postContent, Category category) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.category = category;
    }

    public void updatePost(Post post){
        this.postTitle = post.getPostTitle();
        this.postContent = post.getPostContent();
    }
}
