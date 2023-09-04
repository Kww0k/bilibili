package com.bilibili.commons.cache;

import com.bilibili.commons.domain.entity.Account;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Silvery
 * @since 2023/9/4 10:37
 */
@Component
public class AccountListCache {

    private final ConcurrentHashMap<Integer, Account> accountMap = new ConcurrentHashMap<>();

    public ConcurrentHashMap<Integer, Account> getAccounts() {
        return accountMap;
    }

    public List<Account> getAccountList() {
        return accountMap.values().stream().toList();
    }

    public void saveAccount(Account account) {
        accountMap.put(account.getId(), account);
    }

    public void deleteKey(Integer id) {
        accountMap.remove(id);
    }
}
