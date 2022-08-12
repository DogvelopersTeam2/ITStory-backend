package com.blog.itstory.domain.comment.entity;

import com.blog.itstory.domain.common.BaseEntity;
import com.blog.itstory.domain.post.entity.Post;
import lombok.Builder;
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

    // 게스트 사용자 닉네임
    @Column(nullable = false, length = 30)
    private String commentWriter;

    //  사용자의 댓글 내용
    @Column(nullable = false, length = 500)
    private String commentContent;

    //  댓글이 속한 게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Builder
    public Comment(String commentWriter, String commentContent, Post post) {
        this.commentWriter = commentWriter;
        this.commentContent = commentContent;
        this.post = post;


        post.getComments().add(this); // 순수 객체 상태에서의 오류 발생 대비로, 양방향으로 값을 넣어 줌.
        System.out.println("양방향 실행");
    }

    public void update(String commentWriter, String commentContent) {
        this.commentWriter = commentWriter;
        this.commentContent = commentContent;
    }
}
