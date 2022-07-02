package com.risingtest.wanted.exceptionhandler;

import com.risingtest.wanted.config.BaseResponse;
import com.risingtest.wanted.config.BaseResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ControllerExceptionHandler {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public BaseResponse<BaseResponseStatus> baseExceptionHandler(MethodArgumentTypeMismatchException e){

        logger.info(e.getParameter() + " " + e.getParameter().getParameterType().getName() + ": "+ e.getName() + " : " + (e.getValue()==null?"":e.getValue()) + " Error -  " + e.getCause().getMessage());
        return new BaseResponse<>(BaseResponseStatus.REQUEST_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseResponse<BaseResponseStatus> baseExceptionHandler(HttpRequestMethodNotSupportedException e){

        logger.info(e.getMethod());
        return new BaseResponse<>(BaseResponseStatus.REQUEST_METHOD_ERROR);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public BaseResponse<BaseResponseStatus> baseExceptionHandler(HttpMediaTypeNotSupportedException e){
        logger.info(String.valueOf(e.getContentType()));
        return new BaseResponse<>(BaseResponseStatus.REQUEST_MEDIA_TYPE_ERROR);
    }
}
