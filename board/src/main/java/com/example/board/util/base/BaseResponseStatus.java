//package com.example.board.util.base;
//
//import lombok.Getter;
//
//@Getter
//public enum BaseResponseStatus {
//
//
//    SUCCESS(true, 1000, "요청에 성공했습니다."),
//    BAD_REQUEST_ERROR(false, 4000, "잘못된 요청입니다."),
//    VALIDATION_ERROR(false, 2000, "유효성 검사 실패"),
//    USER_NOT_FOUND(false, 3000, "사용자를 찾을 수 없습니다."),
//    INTERNAL_SERVER_ERROR(false, 5000, "서버 내부 오류"),
//
//    USERID_ALREADY_IN_USE(false, 4000, "이미 사용 중인 아이디입니다."),
//    NICKNAME_ALREADY_IN_USE(false, 4000, "이미 사용 중인 별명입니다."),
//    EMAIL_ALREADY_IN_USE(false, 4000, "이미 사용 중인 이메일입니다.");
//
//    private final boolean isSuccess;
//    private final String message;
//    private final int code;
//
//    BaseResponseStatus(boolean isSuccess, int code, String message) {
//        this.isSuccess = isSuccess;
//        this.code = code;
//        this.message = message;
//    }
//}
