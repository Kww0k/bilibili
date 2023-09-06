package com.bilibili.commons.cache;

import com.bilibili.commons.domain.entity.Account;
import com.bilibili.commons.domain.entity.Danmaku;
import com.bilibili.commons.utils.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.bilibili.commons.constants.AppConstants.ACCOUNT_CACHE;
import static com.bilibili.commons.constants.AppConstants.DANMU_CACHE;

/**
 * @author Silvery
 * @since 2023/9/4 16:41
 */
@Component
@RequiredArgsConstructor
public class DanmakuCache {

    private final RedisCache redisCache;

    public List<Danmaku> getList() {
        return redisCache.getCacheMap(DANMU_CACHE)
                .values()
                .stream()
                .map(Danmaku.class::cast)
                .toList();
    }

    public void save(Danmaku danmaku) {
        redisCache.setCacheMapValue(DANMU_CACHE, DANMU_CACHE + danmaku.getId(), danmaku);
    }

    public Danmaku getOne(Integer id) {
        return (Danmaku) redisCache.getCacheMap(DANMU_CACHE).get(DANMU_CACHE + id);
    }

    public void delete(Integer id) {
        redisCache.delCacheMapValue(DANMU_CACHE, DANMU_CACHE + id);
    }
}
