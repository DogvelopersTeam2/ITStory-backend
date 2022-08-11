package com.blog.itstory.api.post.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Builder
@ApiModel(value = "메인페이지 페이징 정보 반환 객체", description = "페이지 관련 정보와 현재 페이지의 게시글 데이터입니다.")
public class MainPageDto {

    @ApiModelProperty(value = "전체 페이지 수(1번부터 시작)", required = true, example = "3")
    private long totalPages;

    @ApiModelProperty(value = "전체 게시글 수", required = true, example = "30")
    private long totalPostCount;

    @ApiModelProperty(value = "현재 페이지 번호", required = true, example = "1")
    private long currentPage;

    @ApiModelProperty(value = "한 페이지의 게시글 사이즈(기본값: 7)", required = true, example = "7")
    private long sizeofPage;

    @ApiModelProperty(value = "페이지 첫장 여부", required = true, example = "true")
    private boolean isFirstPage;

    @ApiModelProperty(value = "페이지 마지막장 여부", required = true, example = "false")
    private boolean isLastPage;

    @ApiModelProperty(value = "현재 페이지의 데이터", required = true)
    List<GetPostDto> postDtos;
}






























