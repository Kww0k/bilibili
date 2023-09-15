package com.bilibili.commons.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.EmailLoginDTO;
import com.bilibili.commons.domain.dto.InsertAccountDTO;
import com.bilibili.commons.domain.dto.UpdateAccountDTO;
import com.bilibili.commons.domain.entity.Account;
import com.bilibili.commons.domain.vo.AccountAuthVO;
import com.bilibili.commons.domain.vo.AccountMessageVO;
import com.bilibili.commons.domain.vo.AuthVO;
import com.bilibili.commons.domain.vo.UpInfoVO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


/**
 * (Account)表服务接口
 *
 * @author Silvery
 * @since 2023-08-17 15:40:06
 */
public interface AccountService extends IService<Account>, UserDetailsService {

    /**
     * 获取用户列表
     *
     * @author Silvery
     * @since 2023/8/18 14:45
     * @return RestBean<List<AccountAuthVO>>
     */
    RestBean<List<AccountAuthVO>> listAccount(String username);

    /**
     * 插入一个新用户
     *
     * @author Silvery
     * @since 2023/8/18 14:45
     * @param insertAccountDTO 需要插入的用户信息
     * @return RestBean<Void>
     */
    RestBean<Void> insertAccount(InsertAccountDTO insertAccountDTO);

    /**
     * 更新指定用户信息
     *
     * @author Silvery
     * @since 2023/8/18 14:45
     * @param updateAccountDTO 需要更新的用户信息
     * @return RestBean<Void>
     */
    RestBean<Void> updateAccountById(UpdateAccountDTO updateAccountDTO);

    /**
     * 删除指定用户
     *
     * @author Silvery
     * @since 2023/8/18 14:46
     * @param id 删除用户的id
     * @return RestBean<Void>
     */
    RestBean<Void> deleteAccountById(Integer id);

    /**
     * 获取注册邮箱的验证码
     *
     * @author Silvery
     * @since 2023/8/18 16:34
     * @param email 邮箱地址
     * @return RestBean<Void>
     */
    RestBean<Void> getRegisterEmailVerifyCode(String email);

    /**
     * 邮箱注册或则登陆
     *
     * @author Silvery
     * @since 2023/8/18 17:18
     * @param emailLoginDTO 邮箱和验证码
     * @return RestBean
     */
    RestBean<AuthVO> registerOrLogin(EmailLoginDTO emailLoginDTO);

    /**
     * 根据id查询up信息
     *
     * @author Silvery
     * @since 2023/9/6 14:30
     * @param id 用户id
     * @return RestBean<UpInfoVO>
     */
    RestBean<UpInfoVO> getUpInfo(Integer id);

    /**
     * 根据用户id获取他发消息的列表
     *
     * @author Silvery
     * @since 2023/9/15 17:17
     * @param id 用户id
     * @return List<AccountMessageVO>
     */
    List<AccountMessageVO> getAccountMessageListById(Integer id);
}
