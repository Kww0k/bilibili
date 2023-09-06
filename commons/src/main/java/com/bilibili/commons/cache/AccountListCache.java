package com.bilibili.commons.cache;

import com.bilibili.commons.domain.entity.Account;
import com.bilibili.commons.utils.RedisCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.bilibili.commons.constants.AppConstants.ACCOUNT_CACHE;
import static com.bilibili.commons.constants.AppConstants.DANMU_CACHE;

/**
 * @author Silvery
 * @since 2023/9/4 10:37
 */
@Component
@RequiredArgsConstructor
public class AccountListCache {

    private final RedisCache redisCache;

    public List<Account> getAccountList() {
        return redisCache.getCacheMap(ACCOUNT_CACHE)
                .values()
                .stream()
                .map(Account.class::cast)
                .toList();
    }

    public Account getOne(Integer id) {
        return (Account) (redisCache.getCacheMap(ACCOUNT_CACHE).get(ACCOUNT_CACHE + id));
    }

    public void saveAccount(Account account) {
        redisCache.setCacheMapValue(ACCOUNT_CACHE, ACCOUNT_CACHE + account.getId(), account);
    }

    public void deleteKey(Integer id) {
        redisCache.delCacheMapValue(ACCOUNT_CACHE, ACCOUNT_CACHE + id);
    }
}
