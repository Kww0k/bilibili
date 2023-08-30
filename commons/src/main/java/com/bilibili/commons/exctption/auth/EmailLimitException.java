package com.bilibili.commons.exctption.auth;

import com.bilibili.commons.exctption.BaseException;

import static com.bilibili.commons.enums.HttpMessage.EMAIL_LIMIT;

/**
 * @author Silvery
 * @since 2023/8/18 16:53
 */
public class EmailLimitException extends BaseException {
    public EmailLimitException() {
        super(EMAIL_LIMIT.getCode(), EMAIL_LIMIT.getMessage());
    }
}
