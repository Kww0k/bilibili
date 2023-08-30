package com.bilibili.commons.domain;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.bilibili.commons.enums.HttpMessage;

import static com.bilibili.commons.enums.HttpMessage.SUCCESS;

/**
 * @author Silvery
 * @since 2023/8/17 14:24
 */
public record RestBean<T>(int code, T data, String message) {

    public static <T> RestBean<T> success() {
        return new RestBean<>(SUCCESS.getCode(), null, SUCCESS.getMessage());
    }

    public static <T> RestBean<T> success(T data) {
        return new RestBean<>(SUCCESS.getCode(), data, SUCCESS.getMessage());
    }

    public static <T> RestBean<T> failure(int code, String message) {
        return new RestBean<>(code, null, message);
    }

    public static <T> RestBean<T> failure(HttpMessage httpMessage) {
        return new RestBean<>(httpMessage.getCode(), null, httpMessage.getMessage());
    }


    public String toJsonString() {
        return JSONObject.toJSONString(this, JSONWriter.Feature.WriteNulls);
    }
}
