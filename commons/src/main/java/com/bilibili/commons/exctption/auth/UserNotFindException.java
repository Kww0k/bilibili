package com.bilibili.commons.exctption.auth;

import com.bilibili.commons.exctption.BaseException;

import static com.bilibili.commons.enums.HttpMessage.USER_NOT_FIND;

/**
 * @author Silvery
 * @since 2023/8/18 11:36
 */
public class UserNotFindException extends BaseException {
    public UserNotFindException() {
        super(USER_NOT_FIND.getCode(), USER_NOT_FIND.getMessage());
    }
}
