package com.blog.itstory.api.comment.dto;

import com.blog.itstory.domain.comment.entity.Comment;
import com.blog.itstory.domain.post.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentDto {

    @Getter
    public static class Request{

        private String commentWriter;
        private String commentContent;

        // 해당 Dto 인스턴스의 값으로 Entity를 만드는 것이기 때문에, static 이 아니어도 됨.
        public Comment toEntity(Post post){
            return Comment.builder()
                    .commentWriter(commentWriter)
                    .commentContent(commentContent)
                    .post(post)
                    .build();
        }
    }

    @Getter @Setter @Builder
    public static class Response{
        private Long commentId;
        private String commentWriter;
        private String commentContent;
        private String createDateTime;

        public static Response of(Comment comment) {
            String formatted = comment.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            return Response.builder()
                    .commentId(comment.getCommentId())
                    .commentWriter(comment.getCommentWriter())
                    .commentContent(comment.getCommentContent())
                    .createDateTime(formatted)
                    .build();
        }
    }
}
