package com.bilibili.commons.cache;

import com.bilibili.commons.domain.entity.Files;
import com.bilibili.commons.domain.entity.Tag;
import com.bilibili.commons.utils.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.bilibili.commons.constants.AppConstants.FILE_CACHE;
import static com.bilibili.commons.constants.AppConstants.TAG_CACHE;

/**
 * @author Silvery
 * @since 2023/9/4 16:41
 */
@Component
@RequiredArgsConstructor
public class TagCache {

    private final RedisCache redisCache;

    public List<Tag> getList() {
        return redisCache.getCacheMap(TAG_CACHE)
                .values()
                .stream()
                .map(Tag.class::cast)
                .toList();
    }

    public void save(Tag tag) {
        redisCache.setCacheMapValue(TAG_CACHE, TAG_CACHE + tag.getId(), tag);
    }

    public Tag getOne(Integer id) {
        return (Tag) redisCache.getCacheMap(TAG_CACHE).get(TAG_CACHE + id);
    }

    public void delete(Integer id) {
        redisCache.delCacheMapValue(TAG_CACHE, TAG_CACHE + id);
    }
}
