package com.bilibili.auth.runner;

import com.bilibili.commons.cache.AccountListCache;
import com.bilibili.commons.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
        accountMapper.selectList(null).forEach(accountListCache::saveAccount);
    }
}
