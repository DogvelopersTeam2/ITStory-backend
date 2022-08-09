package com.blog.itstory.api.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ApiTestDto {

    private String postTitle;
    private String postContent;
    private List<TestCommentDto> postComments;

    @Getter @Setter @Builder
    public static class TestCommentDto{
        private String commentWriter;
        private String commentContent;
    }
}



























