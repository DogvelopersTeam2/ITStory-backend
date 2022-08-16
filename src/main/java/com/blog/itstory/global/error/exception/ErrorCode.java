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

    // 회원
    MISMATCHED_PASSWORD(400, "비밀번호 입력이 일치하지 않습니다."),
    EMAIL_NOT_EXIST(400, "회원가입된 이메일이 아닙니다."),
    PASSWORD_NOT_MATCHES(400, "비밀번호를 다시 입력해 주세요."),

    //  회원 인증
    NOT_VALID_TOKEN(401, "유효하지않은 토큰 입니다."),
    NOT_ACCESS_TOKEN_TYPE(401, "tokenType이 access token이 아닙니다."),
    ACCESS_TOKEN_EXPIRED(401, "해당 access token은 만료됐습니다."),
    NOT_EXISTS_AUTHORIZATION(401, "Authorization Header가 없습니다."),
    NOT_VALID_BEARER_GRANT_TYPE(401, "인증 타입이 Bearer 타입이 아닙니다."),
    REFRESH_TOKEN_NOT_FOUND(400, "해당 refresh token은 존재하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(401, "해당 refresh token은 만료됐습니다.");

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
