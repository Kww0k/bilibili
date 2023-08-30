package com.bilibili.commons.domain.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author Silvery
 * @since 2023/8/18 11:45
 */
@Data
public class InsertAccountDTO {
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
     * 邮箱地址
     */
    @Email(message = "非法的邮箱地址")
    private String email;

}
