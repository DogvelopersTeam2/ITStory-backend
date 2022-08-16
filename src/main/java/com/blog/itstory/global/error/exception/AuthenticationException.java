package com.blog.itstory.global.error.exception;

public class AuthenticationException extends BusinessException{

    public AuthenticationException(ErrorCode errorCode){
        super(errorCode);
    }
}
