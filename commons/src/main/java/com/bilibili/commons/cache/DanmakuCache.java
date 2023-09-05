package com.bilibili.commons.cache;

import com.bilibili.commons.domain.entity.Danmaku;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Silvery
 * @since 2023/9/4 16:41
 */
@Component
public class DanmakuCache {

    private final ConcurrentHashMap<Integer, Danmaku> map = new ConcurrentHashMap<>();

    public ConcurrentHashMap<Integer, Danmaku> get() {
        return map;
    }

    public List<Danmaku> getList() {
        return map.values().stream().toList();
    }

    public void save(Danmaku danmaku) {
        map.put(danmaku.getId(), danmaku);
    }

    public Danmaku getOnt(Integer id) {
        return map.get(id);
    }

    public void delete(Integer id) {
        map.remove(id);
    }
}
