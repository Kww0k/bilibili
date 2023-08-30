package com.bilibili.commons.exctption.file;

import com.bilibili.commons.exctption.BaseException;

import static com.bilibili.commons.enums.HttpMessage.NOT_PICTURE;

/**
 * @author Silvery
 * @since 2023/8/21 14:13
 */
public class NotPictureException extends BaseException {
    public NotPictureException() {
        super(NOT_PICTURE.getCode(), NOT_PICTURE.getMessage());
    }
}
