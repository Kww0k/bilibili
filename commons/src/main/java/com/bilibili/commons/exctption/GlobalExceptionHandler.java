package com.bilibili.commons.exctption;

import com.bilibili.commons.domain.RestBean;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

import static com.bilibili.commons.constants.HttpStatus.HTTP_STATUS_422;
import static com.bilibili.commons.enums.HttpMessage.SYSTEM_ERROR;

/**
 * @author Silvery
 * @since 2023/8/18 15:02
 */
@ControllerAdvice
@Slf4j
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public RestBean<Void> exceptionHandler(Exception e) {
        if (e instanceof BaseException exception)
            return RestBean.failure(exception.getCode(), exception.getMessage());
        if (e instanceof ConstraintViolationException exception)
            return RestBean.failure(HTTP_STATUS_422, exception.getMessage());
        if (e instanceof MethodArgumentNotValidException exception)
            if (exception.getFieldError() != null)
                return RestBean.failure(HTTP_STATUS_422, exception.getFieldError().getDefaultMessage());
        if (e instanceof SQLException exception)
            return RestBean.failure(exception.getErrorCode(), exception.getMessage());
        return RestBean.failure(SYSTEM_ERROR.getCode(), e.getMessage());
    }
}
