package com.bilibili.commons.cache;

import com.bilibili.commons.domain.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Silvery
 * @since 2023/9/4 16:41
 */
@Component
public class TagCache {

    private final ConcurrentHashMap<Integer, Tag> map = new ConcurrentHashMap<>();

    public ConcurrentHashMap<Integer, Tag> get() {
        return map;
    }

    public List<Tag> getList() {
        return map.values().stream().toList();
    }

    public void save(Tag tag) {
        map.put(tag.getId(), tag);
    }

    public Tag getOnt(Integer id) {
        return map.get(id);
    }

    public void delete(Integer id) {
        map.remove(id);
    }
}
