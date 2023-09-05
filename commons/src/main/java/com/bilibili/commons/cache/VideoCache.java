package com.bilibili.commons.cache;

import com.bilibili.commons.domain.entity.Video;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Silvery
 * @since 2023/9/4 16:41
 */
@Component
public class VideoCache {

    private final ConcurrentHashMap<Integer, Video> map = new ConcurrentHashMap<>();

    public ConcurrentHashMap<Integer, Video> get() {
        return map;
    }

    public List<Video> getList() {
        return map.values().stream().toList();
    }

    public void save(Video video) {
        map.put(video.getId(), video);
    }

    public Video getOnt(Integer id) {
        return map.get(id);
    }

    public void delete(Integer id) {
        map.remove(id);
    }
}
