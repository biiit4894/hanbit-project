//package com.example.board.util.base;
//
//import org.springframework.stereotype.Component;
//
//@Component
//public class BaseResponseServiceImpl implements BaseResponseService {
//    public <T> BaseResponse<Object> getSuccessResponse(T data) {
//        return BaseResponse.builder()
//                .isSuccess(true)
//                .code(BaseResponseStatus.SUCCESS.getCode())
//                .message(BaseResponseStatus.SUCCESS.getMessage())
//                .data(data)
//                .build();
//    }
//
//    public <T> BaseResponse<Object> getSuccessResponse() {
//        return BaseResponse.builder()
//                .isSuccess(true)
//                .code(BaseResponseStatus.SUCCESS.getCode())
//                .message(BaseResponseStatus.SUCCESS.getMessage())
//                .build();
//    }
//
//    public <T> BaseResponse<Object> getFailureResponse(BaseResponseStatus status) {
//        return BaseResponse.builder()
//                .isSuccess(status.isSuccess())
//                .code(status.getCode())
//                .message(status.getMessage())
//                .build();
//    }
//}
