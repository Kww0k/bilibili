package com.bilibili.commons.exctption.video;

import com.bilibili.commons.exctption.BaseException;

import static com.bilibili.commons.enums.HttpMessage.Danmaku_Not_Find;

/**
 * @author Silvery
 * @since 2023/8/28 17:23
 */
public class DanmakuNotFindException extends BaseException {
    public DanmakuNotFindException() {
        super(Danmaku_Not_Find.getCode(), Danmaku_Not_Find.getMessage());
    }
}
