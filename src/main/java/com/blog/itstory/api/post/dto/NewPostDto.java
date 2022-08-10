package com.blog.itstory.api.post.dto;

import com.blog.itstory.domain.comment.entity.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 *  새 글을 등록하고,
 *  등록한 글의 정보를 반환함.
 */
public class NewPostDto {

    @Getter @Setter @Builder
    @ApiModel(value = "새 글을 등록하는 요청 객체", description = "글 작성 요청 시 보내실 객체입니다. 글에 대한 정보를 포함합니다.")
    public static class Request{

        @ApiModelProperty(value = "게시글 제목", required = true, example = "게시글 제목입니다")
        private String postTitle;

        @ApiModelProperty(value = "게시글 본문", required = true, example = "게시글 본문입니다")
        private String postContent;
    }

    @Getter @Setter @Builder
    @ApiModel(value = "새 글을 등록하는 반환 객체", description = "글 작성 요청 후 받으실 객체입니다. 글에 대한 정보를 포함합니다.")
    public static class Response{

        @ApiModelProperty(value = "게시글 ID", required = true, example = "게시글 ID입니다. " +
                "특정 게시글을 식별해 삭제하거나 업데이트하기 위해 존재합니다.")
        private Long postId;

        @ApiModelProperty(value = "게시글 제목", required = true, example = "게시글 제목입니다")
        private String postTitle;

        @ApiModelProperty(value = "게시글 본문", required = true, example = "게시글 본문입니다")
        private String postContent;

        @ApiModelProperty(value = "게시글 작성일", required = true, example = "2022-08-10")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate createTime;
    }


}
