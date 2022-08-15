package com.blog.itstory.domain.post.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

@Getter
public enum Category {

    IOS, FRONTEND, BACKEND;

    //  Json -> DTO 의 역직렬화를 도와주는 JsonCreator. 커스텀 Setter 역할을 한다.
    //  그리고 이 메소드는 @RequestParam 으로 들어오는 커스텀 컨버터에도 등록되어 있음.
    @JsonCreator
    public static Category from(String type){
        return Category.valueOf(type.toUpperCase());
    }
}
