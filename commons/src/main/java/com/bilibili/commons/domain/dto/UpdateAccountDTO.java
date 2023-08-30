package com.bilibili.commons.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author Silvery
 * @since 2023/8/18 11:45
 */
@Data
public class UpdateAccountDTO {
    /**
     * 用户id
     */
    @NotNull(message = "id不能为空")
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 密码
     */
    @Length(min = 8, max = 18, message = "密码长度必须在8-18位")
    private String password;
    /**
     * 邮箱
     */
    @Email(message = "非法的邮箱地址")
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
