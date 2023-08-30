package com.bilibili.commons.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Silvery
 * @since 2023/8/18 16:42
 */
@Component
@RequiredArgsConstructor
public class FlowUtils {

    private final StringRedisTemplate stringRedisTemplate;

    public boolean limitOnceCheck(String key, int blackTime) {
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key)))
            return false;
        else
            stringRedisTemplate.opsForValue().set(key, "", blackTime, TimeUnit.SECONDS);
        return true;
    }

}
