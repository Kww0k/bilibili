package com.bilibili.message.runner;

import com.bilibili.commons.cache.MessageCache;
import com.bilibili.commons.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final MessageCache messageCache;

    private final MessageMapper messageMapper;

    @Override
    public void run(String... args) throws Exception {
        if (messageCache.getList() == null || messageCache.getList().isEmpty())
            messageMapper.selectList(null).forEach(messageCache::save);
    }
}