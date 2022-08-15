package com.blog.itstory.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    /**
     *  에러처리 안내용 ENUM
     */

    HTTP_REQUEST_METHOD_NOT_SUPPORTED(405, "지정하신 URL은 사용하신 HTTP 메소드를 지원하지 않습니다."),
    METHOD_ARGUMENT_TYPE_MISMATCH(400, "유효하지 않은 요청값입니다"),

    //  게시글
    POST_NOT_EXISTS(400, "게시글을 찾을 수 없습니다."),

    //  댓글
    COMMENT_NOT_EXISTS(400, "댓글을 찾을 수 없습니다"),


    //  회원
    MISMATCHED_PASSWORD(400, "비밀번호 입력이 일치하지 않습니다.");



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
