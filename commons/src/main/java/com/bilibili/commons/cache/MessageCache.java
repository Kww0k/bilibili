package com.bilibili.commons.cache;

import com.bilibili.commons.domain.entity.Files;
import com.bilibili.commons.domain.entity.Message;
import com.bilibili.commons.utils.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.bilibili.commons.constants.AppConstants.*;

/**
 * @author Silvery
 * @since 2023/9/15 16:13
 */
@Component
@RequiredArgsConstructor
public class MessageCache {

    private final RedisCache redisCache;

    public List<Message> getList() {
        return redisCache.getCacheMap(MESSAGE_CACHE)
                .values()
                .stream()
                .map(Message.class::cast)
                .toList();
    }

    public void save(Message message) {
        redisCache.setCacheMapValue(MESSAGE_CACHE, MESSAGE_CACHE + message.getId(), message);
    }

    public Message getOne(Integer id) {
        return (Message) redisCache.getCacheMap(MESSAGE_CACHE).get(MESSAGE_CACHE + id);
    }

    public void delete(Integer id) {
        redisCache.delCacheMapValue(MESSAGE_CACHE, MESSAGE_CACHE + id);
    }
}
