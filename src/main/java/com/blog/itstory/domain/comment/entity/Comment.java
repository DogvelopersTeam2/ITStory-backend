package com.blog.itstory.domain.comment.entity;

import com.blog.itstory.domain.common.BaseEntity;
import com.blog.itstory.domain.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@Getter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    // 게스트 사용자의 댓글
    @Column(nullable = false, length = 30)
    private String commentWriter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}
