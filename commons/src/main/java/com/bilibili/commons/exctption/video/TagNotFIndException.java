package com.bilibili.commons.exctption.video;

import com.bilibili.commons.exctption.BaseException;

import static com.bilibili.commons.enums.HttpMessage.TAG_NOT_FIND;

/**
 * @author Silvery
 * @since 2023/8/22 16:12
 */
public class TagNotFIndException extends BaseException {
    public TagNotFIndException() {
        super(TAG_NOT_FIND.getCode(), TAG_NOT_FIND.getMessage());
    }
}
