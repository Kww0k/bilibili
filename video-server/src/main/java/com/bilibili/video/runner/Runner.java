package com.bilibili.video.runner;

import com.bilibili.commons.cache.DanmakuCache;
import com.bilibili.commons.cache.TagCache;
import com.bilibili.commons.cache.VideoCache;
import com.bilibili.commons.mapper.DanmakuMapper;
import com.bilibili.commons.mapper.TagMapper;
import com.bilibili.commons.mapper.VideoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Silvery
 * @since 2023/8/29 11:26
 */
@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final TagMapper tagMapper;

    private final TagCache tagCache;

    private final VideoMapper videoMapper;

    private final VideoCache videoCache;

    private final DanmakuCache danmakuCache;

    private final DanmakuMapper danmakuMapper;

    @Override
    public void run(String... args) {
        if (danmakuCache.getList() ==null || danmakuCache.getList().isEmpty())
            danmakuMapper.selectList(null).forEach(danmakuCache::save);
        if (videoCache.getList() == null || videoCache.getList().isEmpty())
            videoMapper.selectList(null).forEach(videoCache::save);
        if (tagCache.getList() == null || tagCache.getList().isEmpty())
            tagMapper.selectList(null).forEach(tagCache::save);
    }
}
