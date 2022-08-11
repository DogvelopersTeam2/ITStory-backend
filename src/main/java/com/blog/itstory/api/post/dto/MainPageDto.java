package com.blog.itstory.api.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Builder
public class MainPageDto {

    private long totalPages;
    private long totalPostCount;
    private long currentPage;
    private long sizeofPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    List<GetPostDto> postDtos;
}






























