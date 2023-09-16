package com.bilibili.commons.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.commons.cache.AccountListCache;
import com.bilibili.commons.cache.LikesCache;
import com.bilibili.commons.cache.MessageCache;
import com.bilibili.commons.domain.LoginUser;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.EmailLoginDTO;
import com.bilibili.commons.domain.dto.InsertAccountDTO;
import com.bilibili.commons.domain.dto.UpdateAccountDTO;
import com.bilibili.commons.domain.entity.Account;
import com.bilibili.commons.domain.vo.AccountAuthVO;
import com.bilibili.commons.domain.vo.AccountMessageVO;
import com.bilibili.commons.domain.vo.AuthVO;
import com.bilibili.commons.domain.vo.UpInfoVO;
import com.bilibili.commons.exctption.auth.EmailLimitException;
import com.bilibili.commons.exctption.auth.UserNotFindException;
import com.bilibili.commons.exctption.auth.VerifyCodeException;
import com.bilibili.commons.mapper.AccountMapper;
import com.bilibili.commons.service.AccountService;
import com.bilibili.commons.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.bilibili.commons.constants.AppConstants.*;

/**
 * (Account)表服务实现类
 *
 * @author Silvery
 * @since 2023-08-17 15:40:06
 */
@Service("accountService")
@RequiredArgsConstructor
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    private final UuidUtil uuidUtil;

    private final AccountListCache accountListCache;

    private final MessageCache messageCache;

    private final BeanCopyUtils beanCopyUtils;

    private final FlowUtils flowUtils;

    private final RedisCache redisCache;

    private final JavaMailSender javaMailSender;

    private final PasswordEncoder passwordEncoder;

    private final SecurityUtil securityUtil;

    private final LikesCache likesCache;

    private final JwtUtils jwtUtils;

    @Value("${spring.mail.username}")
    String from;

    /**
     * 登陆
     *
     * @author Silvery
     * @since 2023/8/18 14:46
     * @param username 用户名或者邮箱
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = baseMapper.selectOne(new LambdaQueryWrapper<Account>()
                .eq(Account::getUsername, username)
                .or()
                .eq(Account::getEmail, username));
        if (account == null)
            throw new UsernameNotFoundException("用户名或密码错误");
        return new LoginUser(account);
    }

    @Override
    public RestBean<List<AccountAuthVO>> listAccount(String username) {
        List<Account> accountList = accountListCache.getAccountList();
        List<AccountAuthVO> accountAuthVOList = accountList
                .stream()
                .filter(account -> !Objects.equals(account.getId(), securityUtil.getUserId()))
                .filter(account -> !StringUtils.hasText(username) || account.getUsername().contains(username))
                .map(account -> beanCopyUtils.copyBean(account, AccountAuthVO.class))
                .toList();
        return RestBean.success(accountAuthVOList);
    }

    @Override
    public RestBean<Void> insertAccount(InsertAccountDTO insertAccountDTO) {
        if (!StringUtils.hasText(insertAccountDTO.getUsername()))
            insertAccountDTO.setUsername(NAME_START + uuidUtil.getShortUuid());
        if (!StringUtils.hasText(insertAccountDTO.getNickname()))
            insertAccountDTO.setNickname(NAME_START + (new Random().nextInt(89999999) + 10000000));
        insertAccountDTO.setPassword(passwordEncoder.encode(insertAccountDTO.getPassword()));
        Account account = beanCopyUtils.copyBean(insertAccountDTO, Account.class);
        baseMapper.insert(account);
        accountListCache.saveAccount(baseMapper.selectById(account.getId()));
        return RestBean.success();
    }

    @Override
    public RestBean<Void> updateAccountById(UpdateAccountDTO updateAccountDTO) {
        if (StringUtils.hasText(updateAccountDTO.getPassword()))
            updateAccountDTO.setPassword(passwordEncoder.encode(updateAccountDTO.getPassword()));
        if (baseMapper.updateById(beanCopyUtils.copyBean(updateAccountDTO, Account.class)) == FALSE_CODE)
            throw new UserNotFindException();
        accountListCache.saveAccount(baseMapper.selectById(updateAccountDTO.getId()));
        return RestBean.success();
    }

    @Override
    public RestBean<Void> deleteAccountById(Integer id) {
        if (baseMapper.deleteById(id) == FALSE_CODE)
            throw new UserNotFindException();
        accountListCache.deleteKey(id);
        return RestBean.success();
    }

    @Override
    public RestBean<Void> getRegisterEmailVerifyCode(String email) {
        // 加锁
        synchronized (email.intern()) {
            if (!verifyLimit(email))
                throw new EmailLimitException();
            // 准备邮件
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("【哔哩哔哩】");
            Random random = new Random();
            int code = random.nextInt(899999) + 100000;
            message.setText("【哔哩哔哩" + code + "短信登录验证码，5分钟内有效，请勿泄露。");
            message.setTo(email);
            message.setFrom(from);
            // 存入redis并发送
            redisCache.setCacheObject(REGISTER_EMAIL + email, String.valueOf(code), EMAIL_TIME, TimeUnit.MINUTES); // 过期时间为5分钟
            javaMailSender.send(message);
            return RestBean.success();
        }

    }

    @Override
    public RestBean<AuthVO> registerOrLogin(EmailLoginDTO emailLoginDTO) {
        // 获取code
        String code = redisCache.getCacheObject(REGISTER_EMAIL + emailLoginDTO.getEmail());
        // 不存在或为空则报错
        if (code == null || !Objects.equals(code, emailLoginDTO.getCode()))
            throw new VerifyCodeException();
        // 查找数据库中是否存在
        Account account = baseMapper.selectOne(new LambdaQueryWrapper<Account>().eq(Account::getEmail, emailLoginDTO.getEmail()));
        // 不存在则新增一条数据
        if (account == null) {
            account = new Account();
            account.setEmail(emailLoginDTO.getEmail());
            account.setUsername(NAME_START +uuidUtil.getShortUuid());
            account.setNickname(NAME_START + (new Random().nextInt(89999999) + 10000000));
            baseMapper.insert(account);
            accountListCache.saveAccount(baseMapper.selectById(account.getId()));
        }
        redisCache.deleteObject(REGISTER_EMAIL + emailLoginDTO.getEmail());
        // 创建jwt 并返回
        LoginUser user = new LoginUser(account);
        String token = jwtUtils.createJwt(user);
        AccountAuthVO accountAuthVO = beanCopyUtils.copyBean(user.getAccount(), AccountAuthVO.class);
        AuthVO authVO = new AuthVO(accountAuthVO, token);
        return RestBean.success(authVO);
    }

    @Override
    public RestBean<UpInfoVO> getUpInfo(Integer id) {
        return RestBean.success(beanCopyUtils.copyBean(accountListCache.getOne(id), UpInfoVO.class));
    }

    @Override
    public List<AccountMessageVO> getAccountMessageListById(Integer id) {
        return likesCache.getList()
                .stream()
                .filter(likes -> Objects.equals(likes.getAccountId(), id))
                .map(likes -> beanCopyUtils.copyBean(accountListCache.getOne(likes.getLikesId()), AccountMessageVO.class))
                .toList();
    }


    private boolean verifyLimit(String email) {
        // 加上一个一分钟的限制时间
        String key = VERIFY_LIMIT + email;
        return flowUtils.limitOnceCheck(key, FLOW_TIME);
    }
}
