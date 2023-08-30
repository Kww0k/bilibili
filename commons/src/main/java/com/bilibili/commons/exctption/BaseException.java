package com.bilibili.commons.exctption;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Silvery
 * @since 2023/8/16 09:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException{
    protected Integer code;
    protected String message;

    public BaseException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}