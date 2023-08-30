package com.bilibili.auth.controller.admin;

import com.bilibili.commons.annotation.SystemLog;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.InsertAccountDTO;
import com.bilibili.commons.domain.dto.UpdateAccountDTO;
import com.bilibili.commons.domain.vo.AccountAuthVO;
import com.bilibili.commons.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Silvery
 * @since 2023/8/18 11:12
 */
@RestController
@RequestMapping("/admin/account")
@RequiredArgsConstructor
@Validated
public class AdminAccountController {

    private final AccountService accountService;

    @GetMapping("/listAccount")
    @SystemLog(businessName = "获取用户列表")
    public RestBean<List<AccountAuthVO>> listAccount(String username) {
        return accountService.listAccount(username);
    }

    @PostMapping("/insertAccount")
    @SystemLog(businessName = "新增用户")
    public RestBean<Void> insertAccount(@Valid @RequestBody InsertAccountDTO insertAccountDTO) {
        return accountService.insertAccount(insertAccountDTO);
    }

    @PutMapping("/updateAccountById")
    @SystemLog(businessName = "更新用户信息")
    public RestBean<Void> updateAccountById(@Valid @RequestBody UpdateAccountDTO updateAccountDTO) {
        return accountService.updateAccountById(updateAccountDTO);
    }

    @DeleteMapping("/deleteAccountById/{id}")
    @SystemLog(businessName = "删除用户")
    public RestBean<Void> deleteAccountById(@PathVariable Integer id) {
        return accountService.deleteAccountById(id);
    }
}
