package com.bilibili.commons.exctption.file;

import com.bilibili.commons.exctption.BaseException;

import static com.bilibili.commons.enums.HttpMessage.VIDEO_SIZE_MAX;

/**
 * @author Silvery
 * @since 2023/8/21 14:11
 */
public class VideoMaxException extends BaseException {
    public VideoMaxException() {
        super(VIDEO_SIZE_MAX.getCode(), VIDEO_SIZE_MAX.getMessage());
    }
}
