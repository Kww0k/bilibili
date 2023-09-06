package com.bilibili.commons.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author Silvery
 * @since 2023/8/18 17:16
 */
@Data
public class EmailLoginDTO {
    @Email(message = "非法的邮箱地址")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @Length(max = 6, min = 6, message = "验证码格式错误")
    private String code;
}
