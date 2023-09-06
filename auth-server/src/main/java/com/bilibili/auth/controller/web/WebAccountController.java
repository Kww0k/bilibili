package com.bilibili.auth.controller.web;

import com.bilibili.commons.annotation.SystemLog;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.EmailLoginDTO;
import com.bilibili.commons.domain.vo.AuthVO;
import com.bilibili.commons.domain.vo.UpInfoVO;
import com.bilibili.commons.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Silvery
 * @since 2023/8/17 17:07
 */
@RestController
@RequestMapping("/web/account")
@RequiredArgsConstructor
@Validated
public class WebAccountController {

    private final AccountService accountService;


    @SystemLog(businessName = "获取验证码")
    @GetMapping("/getRegisterEmailVerifyCode")
    public RestBean<Void> getRegisterEmailVerifyCode(@Length(max = 100, min = 7, message = "邮箱必须在7-100为") @Email(message = "非法的邮箱地址") String email) {
        return accountService.getRegisterEmailVerifyCode(email);
    }

    @SystemLog(businessName = "注册或登陆用户")
    @PostMapping("/registerOrLogin")
    public RestBean<AuthVO> registerOrLogin(@Valid @RequestBody EmailLoginDTO emailLoginDTO) {
        return accountService.registerOrLogin(emailLoginDTO);
    }

    @SystemLog(businessName = "获取up信息")
    @GetMapping("/getUpInfo/{id}")
    public RestBean<UpInfoVO> getUpInfo(@PathVariable Integer id) {
        return accountService.getUpInfo(id);
    }
}
