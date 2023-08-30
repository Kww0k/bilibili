package com.bilibili.commons.exctption.auth;

import com.bilibili.commons.exctption.BaseException;

import static com.bilibili.commons.enums.HttpMessage.EMAIL_LIMIT;

/**
 * @author Silvery
 * @since 2023/8/18 17:22
 */
public class VerifyCodeException extends BaseException {
    public VerifyCodeException() {
        super(EMAIL_LIMIT.getCode(), EMAIL_LIMIT.getMessage());
    }
}
