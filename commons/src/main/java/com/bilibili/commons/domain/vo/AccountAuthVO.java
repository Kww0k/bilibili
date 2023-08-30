package com.bilibili.commons.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Silvery
 * @since 2023/8/17 16:28
 */
@Data
@Accessors(chain = true)
public class AccountAuthVO {
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    private String nickname;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 头像地址
     */
    private String url;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 性别
     */
    private String sex;
    /**
     * 学校
     */
    private String school;
    /**
     * 签名
     */
    private String signature;
}
