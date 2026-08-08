package org.example.Config;

import org.example.HttpResult;
import org.example.exception.BusinessException;
import org.example.exception.LoginException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginException.class)
    public HttpResult handleLoginException(LoginException e) {
        return new HttpResult(null, e.getMessage(), e.getCode());
    }

    @ExceptionHandler(BusinessException.class)
    public HttpResult handleBusinessException(BusinessException e) {
        return new HttpResult(null, e.getMessage(), e.getCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpResult handleValidException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldError() != null
                ? e.getBindingResult().getFieldError().getDefaultMessage()
                : "参数校验失败";
        return new HttpResult(null, msg, 400);
    }

    @ExceptionHandler(Exception.class)
    public HttpResult handleException(Exception e) {
        return new HttpResult(null, "系统异常：" + e.getMessage(), 500);
    }
}
