package com.blog.itstory.domain.post.constant;

public enum Category {
    IOS, FRONTEND, BACKEND;

    public static Category from(String type){
        return Category.valueOf(type.toUpperCase());
    }
}
