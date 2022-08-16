package com.blog.itstory.global.error.exception;

public class TokenNotFoundException extends BusinessException {
    public TokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
