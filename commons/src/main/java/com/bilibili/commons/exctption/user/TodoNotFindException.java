package com.bilibili.commons.exctption.user;

import com.bilibili.commons.exctption.BaseException;

import static com.bilibili.commons.enums.HttpMessage.TODO_NOT_FIND;

/**
 * @author Silvery
 * @since 2023/8/25 10:13
 */
public class TodoNotFindException extends BaseException {
    public TodoNotFindException() {
        super(TODO_NOT_FIND.getCode(), TODO_NOT_FIND.getMessage());
    }
}
