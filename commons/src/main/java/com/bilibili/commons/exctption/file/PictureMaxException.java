package com.bilibili.commons.exctption.file;

import com.bilibili.commons.exctption.BaseException;

import static com.bilibili.commons.enums.HttpMessage.PICTURE_SIZE_MAX;

/**
 * @author Silvery
 * @since 2023/8/21 14:10
 */
public class PictureMaxException extends BaseException {
    public PictureMaxException() {
        super(PICTURE_SIZE_MAX.getCode(), PICTURE_SIZE_MAX.getMessage());
    }
}
