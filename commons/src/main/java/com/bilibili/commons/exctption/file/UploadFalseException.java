package com.bilibili.commons.exctption.file;

import com.bilibili.commons.exctption.BaseException;

import static com.bilibili.commons.enums.HttpMessage.UPLOAD_ERROR;

/**
 * @author Silvery
 * @since 2023/8/21 14:13
 */
public class UploadFalseException extends BaseException {
    public UploadFalseException() {
        super(UPLOAD_ERROR.getCode(), UPLOAD_ERROR.getMessage());
    }
}
