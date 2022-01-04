package com.idolcollector.idolcollector.advice;

import com.idolcollector.idolcollector.advice.exception.*;
import com.idolcollector.idolcollector.service.ResponseService;
import com.idolcollector.idolcollector.web.dto.response.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.*;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExControllerAdvice {

    private final ResponseService responseService;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public CommonResult exHandler(Exception e) {
        log.error("[exceptionHandler} ex", e);
        return responseService.getFailResult(-100, "알 수 없는 내부 오류가 발생했습니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public CommonResult cMemberNotFoundException(CMemberNotFoundException e) {
        log.error("[exceptionHandler} ex", e);
        return responseService.getFailResult(-101, "존재하지 않은 회원입니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public CommonResult cPostNotFoundException(CPostNotFoundException e) {
        log.error("[exceptionHandler} ex", e);
        return responseService.getFailResult(-102, "존재하지 않은 카드입니다.");

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public CommonResult cBundleNotFoundException(CBundleNotFoundException e) {
        log.error("[exceptionHandler} ex", e);
        return responseService.getFailResult(-103, "존재하지 않은 카드집입니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public CommonResult cNotLoginedException(CNotLoginedException e) {
        log.error("[exceptionHandler} ex", e);
        return responseService.getFailResult(-200, "로그인 되어있지 않습니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public CommonResult accessDeniedException(AccessDeniedException e) {
        log.error("[exceptionHandler} ex", e);
        return responseService.getFailResult(-201, "보유 권한으로 접근할 수 없습니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public CommonResult accessDeniedException(CAccessDeniedException e) {
        log.error("[exceptionHandler} ex", e);
        return responseService.getFailResult(-202, "변경 권한이 없습니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public CommonResult illegalExHandler(IllegalArgumentException e) {
        log.info("[exceptionHandler] ex, {}, message = {}", e, e.getMessage());
        return responseService.getFailResult(-1, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public CommonResult validationExHandler(BindException e) {
        log.error("[exceptionHandler} ex", e);
        FieldError err = e.getFieldError();
        return responseService.getFailResult(-301, String.valueOf(err.getObjectName()) + " " + String.valueOf(err.getField()) + " " + err.getDefaultMessage());
        //return new BindingError(err.getObjectName(), err.getField(), err.getCode(), err.getDefaultMessage());

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public CommonResult reqParamExHandler(MissingServletRequestParameterException e) {
        log.error("[exceptionHandler} ex", e);
        return responseService.getFailResult(-302, "파라미터 값이 없습니다.");

        //return new ErrorResult(-1, "파라미터 값이 없습니다.");
    }
}
