package com.example.board.util.base;

public interface BaseResponseService {

    <T> BaseResponse<Object> getSuccessResponse(T data);

    <T> BaseResponse<Object> getSuccessResponse();

    <T> BaseResponse<Object> getFailureResponse(BaseResponseStatus status);
}
