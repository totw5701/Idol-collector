package com.idolcollector.idolcollector.advice;

import com.idolcollector.idolcollector.advice.exception.CBundleNotFoundException;
import com.idolcollector.idolcollector.advice.exception.CMemberNotFoundException;
import com.idolcollector.idolcollector.advice.exception.CNotLoginedException;
import com.idolcollector.idolcollector.advice.exception.CPostNotFoundException;
import com.idolcollector.idolcollector.web.dto.BindingError;
import com.idolcollector.idolcollector.web.dto.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.*;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("[exceptionHandler} ex", e);
        return new ErrorResult(-100, "알 수 없는 내부 오류가 발생했습니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult cMemberNotFoundException(CMemberNotFoundException e) {
        log.error("[exceptionHandler} ex", e);
        return new ErrorResult(-101, "존재하지 않은 회원입니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult cPostNotFoundException(CPostNotFoundException e) {
        log.error("[exceptionHandler} ex", e);
        return new ErrorResult(-102, "존재하지 않은 카드입니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult cBundleNotFoundException(CBundleNotFoundException e) {
        log.error("[exceptionHandler} ex", e);
        return new ErrorResult(-103, "존재하지 않은 카드집입니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult cNotLoginedException(CNotLoginedException e) {
        log.error("[exceptionHandler} ex", e);
        return new ErrorResult(-200, "로그인 되어있지 않습니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult accessDeniedException(AccessDeniedException e) {
        log.error("[exceptionHandler} ex", e);
        return new ErrorResult(-201, "보유 권한으로 접근할 수 없습니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.info("[exceptionHandler] ex, {}, message = {}", e, e.getMessage());
        return new ErrorResult(-1, e.getMessage());
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
        return new ErrorResult(-1, "파라미터 값이 없습니다.");
    }
}
