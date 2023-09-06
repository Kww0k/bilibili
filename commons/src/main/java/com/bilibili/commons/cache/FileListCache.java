package com.bilibili.commons.cache;

import com.bilibili.commons.domain.entity.Danmaku;
import com.bilibili.commons.domain.entity.Files;
import com.bilibili.commons.utils.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.bilibili.commons.constants.AppConstants.DANMU_CACHE;
import static com.bilibili.commons.constants.AppConstants.FILE_CACHE;

/**
 * @author Silvery
 * @since 2023/9/4 11:01
 */
@Component
@RequiredArgsConstructor
public class FileListCache {

    private final RedisCache redisCache;

    public List<Files> getList() {
        return redisCache.getCacheMap(FILE_CACHE)
                .values()
                .stream()
                .map(Files.class::cast)
                .toList();
    }

    public void save(Files files) {
        redisCache.setCacheMapValue(FILE_CACHE, FILE_CACHE + files.getId(), files);
    }

    public Files getOne(Integer id) {
        return (Files) redisCache.getCacheMap(FILE_CACHE).get(FILE_CACHE + id);
    }

    public void delete(Integer id) {
        redisCache.delCacheMapValue(FILE_CACHE, FILE_CACHE + id);
    }
}
