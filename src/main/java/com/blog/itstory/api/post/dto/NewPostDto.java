package com.blog.itstory.api.post.dto;

import com.blog.itstory.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 *  새 글을 등록하고,
 */
public class NewPostDto {

    @Getter @Setter @Builder
    public static class Request{

        private String postTitle;

        private String postContent;
    }
    @Getter @Setter @Builder
    @AllArgsConstructor
    public static class Response{
        private Long postId;
    }


}
