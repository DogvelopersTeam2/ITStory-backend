package com.blog.itstory.api.post.dto;

import com.blog.itstory.domain.post.constant.Category;
import com.blog.itstory.domain.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @Builder
@ApiModel(value = "전체 게시글 조회 반환 객체", description = "전체 게시글 내용을 담은 객체입니다.")
public class GetPostDto {

    @ApiModelProperty(value = "게시글 ID", required = true, example = "1")
    private Long postId;

    @ApiModelProperty(value = "게시글 제목", required = true, example = "게시글 제목입니다.")
    private String postTitle;

    @ApiModelProperty(value = "게시글 카테고리", required = true, example = "BACKEND")
    private Category postCategory;

    @ApiModelProperty(value = "게시글 본문", required = true, example = "게시글 본문")
    private String postContent;

    @ApiModelProperty(value = "게시글에 소속된 댓글 수", required = true, example = "5")
    private int commentCount;

    @ApiModelProperty(value = "게시글 작성일", required = true, example = "2022-08-10")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Instant createTime;

    public static List<GetPostDto> ofList(List<Post> posts){
        return posts.stream()
                .map(post -> GetPostDto.of(post))
                .collect(Collectors.toList());
    }

    public static GetPostDto of(Post post){
        return GetPostDto.builder()
                .postId(post.getPostId())
                .postTitle(post.getPostTitle())
                .postCategory(post.getCategory())
                .postContent(post.getPostContent())
                .commentCount(post.getComments().size())
                //.createTime(post.getCreateTime())
                .build();

    }
}


















