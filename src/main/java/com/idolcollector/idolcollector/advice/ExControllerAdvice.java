package com.idolcollector.idolcollector.advice;

import com.idolcollector.idolcollector.web.dto.BindingError;
import com.idolcollector.idolcollector.web.dto.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.*;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.info("[exceptionHandler] ex, {}, message = {}", e, e.getMessage());

        return new ErrorResult(HttpStatus.BAD_REQUEST.toString(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public BindingError validationExHandler(BindException e) {
        log.error("[exceptionHandler} ex", e);

        FieldError err = e.getFieldError();

        return new BindingError(err.getObjectName(), err.getField(), err.getCode(), err.getDefaultMessage());

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult reqParamExHandler(MissingServletRequestParameterException e) {
        log.error("[exceptionHandler} ex", e);

        return new ErrorResult(HttpStatus.BAD_REQUEST.toString(), "파라미터 값이 없습니다.");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler} ex", e);

        return new ErrorResult(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "알 수 없는 내부 오류");
    }
}
