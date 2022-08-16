package com.blog.itstory.api.comment.dto;

import com.blog.itstory.domain.comment.entity.Comment;
import com.blog.itstory.domain.post.entity.Post;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class CommentDto {

    @ApiModel(value = "댓글 작성 / 수정 요청 객체")
    @Getter
    public static class Request{

        @ApiModelProperty(value = "댓글 작성자", required = true, example = "김경민")
        @Size(min = 1, max = 30, message = "댓글 작성자의 이름은 1~30자로 해 주세요.")
        private String commentWriter;

        @ApiModelProperty(value = "댓글 내용", required = true, example = "김경민의 헛소리")
        @Size(min = 1, max = 500, message = "댓글 내용은 1~500자로 해 주세요.")
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

        @ApiModelProperty(value = "댓글 식별자", required = true, example = "5")
        private Long commentId;

        @ApiModelProperty(value = "댓글 작성자", required = true, example = "김경민")
        private String commentWriter;

        @ApiModelProperty(value = "댓글 내용", required = true, example = "김경민의 헛소리")
        private String commentContent;

        @ApiModelProperty(value = "댓글 작성 시간", required = true, example = "2020-08-02 13:42")
        private String createDateTime;

        public static Response of(Comment comment) {
            //String formatted = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.systemDefault()).format(comment.getCreateTime());
             String formatted = comment.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            return Response.builder()
                    .commentId(comment.getCommentId())
                    .commentWriter(comment.getCommentWriter())
                    .commentContent(comment.getCommentContent())
                    .createDateTime(formatted)
                    .build();
        }

        public static List<Response> ofList(List<Comment> comments) {
            return comments.stream()
                    .map(com -> CommentDto.Response.of(com))
                    .collect(Collectors.toList());
        }
    }
}
