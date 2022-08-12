package com.blog.itstory.api.post.dto;

import com.blog.itstory.api.comment.dto.CommentDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Builder
@ApiModel(value = "게시글 단건 조회 객체", description = "단건 조회에 필요한 게시글 정보와 댓글정보")
public class SinglePostDto {

    @ApiModelProperty(value = "게시글(단건) 조회")
    private GetPostDto post;

    @ApiModelProperty(value = "게시글 소속 댓글들 ")
    private List<CommentDto.Response> comments;
}
