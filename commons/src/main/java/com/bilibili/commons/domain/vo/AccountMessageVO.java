package com.bilibili.commons.domain.vo;

import lombok.Data;

/**
 * @author Silvery
 * @since 2023/9/15 17:14
 */
@Data
public class AccountMessageVO {
    private Integer id;
    /**
     * 用户名
     */
    private String username;

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
