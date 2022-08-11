package com.blog.itstory.api.post.dto;


import com.blog.itstory.domain.post.entity.Post;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class UpdatePostDto {

    @Getter @Setter @Builder
    @ApiModel(value = "게시글 업데이트 API 요청 객체", description = "업데이트 요청 시 해당 객체 예시와 같이 요청하세요!")
    public static class Request{

        @ApiModelProperty(value = "게시글 제목", required = true, example = "게시글 제목입니다")
        private String postTitle;

        @ApiModelProperty(value = "게시글 본문", required = true, example = "게시글 본문입니다")
        private String postContent;

        public Post toEntity() {
            return Post.builder()
                    .postTitle(postTitle)
                    .postContent(postContent)
                    .build();
        }
    }

    @Getter @Setter @Builder
    @ApiModel(value = "게시글 업데이트 API 응답 객체", description = "업데이트 이후 해당 예시와 같이 응답할게요!")
    public static class Response{

        @ApiModelProperty(value = "게시글 제목", required = true, example = "게시글 제목입니다")
        private String postTitle;

        @ApiModelProperty(value = "게시글 본문", required = true, example = "게시글 본문입니다")
        private String postContent;

        public static Response of(Post savedPost) {
            return Response.builder()
                    .postTitle(savedPost.getPostTitle())
                    .postContent(savedPost.getPostContent())
                    .build();
        }
    }
}
