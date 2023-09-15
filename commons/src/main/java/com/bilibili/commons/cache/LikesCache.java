package com.bilibili.commons.cache;

import com.bilibili.commons.domain.entity.Likes;
import com.bilibili.commons.domain.entity.Message;
import com.bilibili.commons.utils.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.bilibili.commons.constants.AppConstants.LIKES_CACHE;
import static com.bilibili.commons.constants.AppConstants.MESSAGE_CACHE;

/**
 * @author Silvery
 * @since 2023/9/15 16:13
 */
@Component
@RequiredArgsConstructor
public class LikesCache {

    private final RedisCache redisCache;

    public List<Likes> getList() {
        return redisCache.getCacheMap(LIKES_CACHE)
                .values()
                .stream()
                .map(Likes.class::cast)
                .toList();
    }

    public void save(Likes likes) {
        redisCache.setCacheMapValue(LIKES_CACHE, likes.getAccountId() + LIKES_CACHE + likes.getLikesId(), likes);
    }

    public void delete(Integer accountId, Integer likesId) {
        redisCache.delCacheMapValue(MESSAGE_CACHE, accountId + MESSAGE_CACHE + likesId);
    }
}
