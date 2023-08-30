package com.bilibili.commons.exctption.file;

import com.bilibili.commons.exctption.BaseException;

import static com.bilibili.commons.enums.HttpMessage.NOT_VIDEO;

/**
 * @author Silvery
 * @since 2023/8/21 14:14
 */
public class NotVideoException extends BaseException {
    public NotVideoException() {
        super(NOT_VIDEO.getCode(), NOT_VIDEO.getMessage());
    }
}
