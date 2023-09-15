package com.bilibili.commons.enums;

import lombok.Getter;

import static com.bilibili.commons.constants.HttpStatus.*;

/**
 * @author Silvery
 * @since 2023/8/17 14:33
 */
@Getter
public enum HttpMessage {

    SUCCESS(HTTP_STATUS_200, "操作成功"),
    SUCCESS_MESSAGE(HTTP_STATUS_201, "操作成功"),
    LOGOUT_ERROR(HTTP_STATUS_400, "退出登陆失败"),
    EMAIL_LIMIT(HTTP_STATUS_400, "请求过于频繁，请等待一分钟"),
    USER_NOT_FIND(HTTP_STATUS_404, "用户不存在"),
    FILE_SIZE_LESS(HTTP_STATUS_413, "文件大小不能低于5KB"),
    PICTURE_SIZE_MAX(HTTP_STATUS_413, "图片大小不能超过10MB"),
    VIDEO_SIZE_MAX(HTTP_STATUS_413, "视频大小不能超过1GB"),
    NOT_PICTURE(HTTP_STATUS_414, "上传内容不是图片格式"),
    NOT_VIDEO(HTTP_STATUS_414, "上传内容不是视频格式"),
    TAG_NOT_FIND(HTTP_STATUS_404, "标签不存在"),
    TODO_NOT_FIND(HTTP_STATUS_404, "事物不存在"),
    VIDEO_INFO_NOT_FIND(HTTP_STATUS_404, "视频不存在"),
    Danmaku_Not_Find(HTTP_STATUS_404, "弹幕不存在"),
    UPLOAD_ERROR(HTTP_STATUS_500, "上传失败"),
    SYSTEM_ERROR(HTTP_STATUS_500, "系统错误");

    final Integer code;

    final String message;

    HttpMessage(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
