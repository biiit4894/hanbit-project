//package com.example.board.util.base;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@Slf4j
//@RequiredArgsConstructor
//public abstract class BaseController {
//    private final BaseResponseService baseResponseService;
//
//    protected <T> ResponseEntity<BaseResponse<Object>> successResponse(T data) {
//        return ResponseEntity.ok(baseResponseService.getSuccessResponse(data));
//    }
//
//    protected <T> ResponseEntity<BaseResponse<Object>> successResponse() {
//        return ResponseEntity.ok(baseResponseService.getSuccessResponse());
//    }
//
//    protected ResponseEntity<BaseResponse<Object>> failureResponse(Exception e) {
//        return ResponseEntity.badRequest().body(baseResponseService.getFailureResponse(BaseResponseStatus.BAD_REQUEST_ERROR));
//    }
//
//}
