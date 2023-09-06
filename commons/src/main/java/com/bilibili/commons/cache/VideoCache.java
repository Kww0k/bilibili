package com.bilibili.commons.cache;

import com.bilibili.commons.domain.entity.Tag;
import com.bilibili.commons.domain.entity.Todo;
import com.bilibili.commons.domain.entity.Video;
import com.bilibili.commons.utils.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.bilibili.commons.constants.AppConstants.*;

/**
 * @author Silvery
 * @since 2023/9/4 16:41
 */
@Component
@RequiredArgsConstructor
public class VideoCache {

    private final RedisCache redisCache;

    public List<Video> getList() {
        return redisCache.getCacheMap(VIDEO_CACHE)
                .values()
                .stream()
                .map(Video.class::cast)
                .toList();
    }

    public void save(Video video) {
        redisCache.setCacheMapValue(VIDEO_CACHE, VIDEO_CACHE + video.getId(), video);
    }

    public Video getOne(Integer id) {
        return (Video) redisCache.getCacheMap(VIDEO_CACHE).get(VIDEO_CACHE + id);
    }

    public void delete(Integer id) {
        redisCache.delCacheMapValue(VIDEO_CACHE, VIDEO_CACHE + id);
    }
}
