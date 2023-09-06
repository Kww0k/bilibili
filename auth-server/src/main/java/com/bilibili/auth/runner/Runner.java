package com.bilibili.auth.runner;

import com.bilibili.commons.cache.AccountListCache;
import com.bilibili.commons.domain.entity.Account;
import com.bilibili.commons.mapper.AccountMapper;
import com.bilibili.commons.utils.BeanCopyUtils;
import com.bilibili.commons.utils.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Silvery
 * @since 2023/8/29 11:26
 */
@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final AccountMapper accountMapper;

    private final AccountListCache accountListCache;

    @Override
    public void run(String... args) {
        if (accountListCache.getAccountList() == null || accountListCache.getAccountList().isEmpty())
            accountMapper.selectList(null).forEach(accountListCache::saveAccount);
    }
}
