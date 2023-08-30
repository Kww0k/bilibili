package com.bilibili.commons.exctption.video;

import com.bilibili.commons.exctption.BaseException;

import static com.bilibili.commons.enums.HttpMessage.VIDEO_INFO_NOT_FIND;

/**
 * @author Silvery
 * @since 2023/8/25 16:13
 */
public class VideoInfoNotFindException extends BaseException {
    public VideoInfoNotFindException() {
        super(VIDEO_INFO_NOT_FIND.getCode(), VIDEO_INFO_NOT_FIND.getMessage());
    }
}
