package com.blog.itstory.domain.jwt.constant;

import lombok.Getter;

@Getter // GrantType.BEARER.getType() 에 쓰임
public enum GrantType {

    BEARER("Bearer");

    GrantType(String type){
        this.type = type;
    }

    private String type;
}
