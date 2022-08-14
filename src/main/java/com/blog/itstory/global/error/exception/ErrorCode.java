package com.blog.itstory.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //  예외처리 한글 안내용
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(405, "지정하신 URL은 사용하신 HTTP 메소드를 지원하지 않습니다."),
    METHOD_ARGUMENT_TYPE_MISMATCH(400, "유효하지 않은 요청값입니다"),
    UNKNOWN_EXCEPTION(400, "알 수 없는 오류입니다.");

    private int status;
    private String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
    ErrorCode(String message){
        this.message = message;
    }
}
