package com.bilibili.commons.domain.vo;

import lombok.Data;

/**
 * @author Silvery
 * @since 2023/9/6 14:28
 */
@Data
public class UpInfoVO {
    private Integer id;
    private String nickname;
    /**
     * 头像地址
     */
    private String url;
    /**
     * 签名
     */
    private String signature;
}
