package com.example.board.util.base;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {


    SUCCESS(true, 1000, "요청에 성공했습니다."),
    BAD_REQUEST_ERROR(false, 4000, "잘못된 요청입니다."),
    VALIDATION_ERROR(false, 2000, "유효성 검사 실패"),
    USER_NOT_FOUND(false, 3000, "사용자를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(false, 5000, "서버 내부 오류");

    private boolean isSuccess;
    private String message;
    private int code;

    BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
