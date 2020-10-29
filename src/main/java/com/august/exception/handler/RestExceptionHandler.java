package com.august.exception.handler;

import com.august.entity.Result;
import com.august.exception.code.RestCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/15
 */
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception e){
        log.error(e.getMessage());
        return Result.get(RestCode.SYSTEM_ERROR);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Result unauthorizedException(UnauthorizedException e){
        log.error("UnauthorizedException,{},{}",e.getLocalizedMessage(),e);
        return Result.get(RestCode.NO_PERMISSION);
    }
}
