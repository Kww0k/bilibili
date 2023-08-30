package com.bilibili.commons.exctption.file;

import com.bilibili.commons.exctption.BaseException;

import static com.bilibili.commons.enums.HttpMessage.FILE_SIZE_LESS;

/**
 * @author Silvery
 * @since 2023/8/21 14:07
 */
public class MinFileSIzeException extends BaseException {
    public MinFileSIzeException() {
        super(FILE_SIZE_LESS.getCode(), FILE_SIZE_LESS.getMessage());
    }
}
