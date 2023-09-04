package com.bilibili.commons.cache;

import com.bilibili.commons.domain.entity.Files;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Silvery
 * @since 2023/9/4 11:01
 */
@Component
public class FileListCache {

    private final ConcurrentHashMap<Integer, Files> map = new ConcurrentHashMap<>();

    public ConcurrentHashMap<Integer, Files> get() {
        return map;
    }

    public List<Files> getList() {
        return map.values().stream().toList();
    }

    public void save(Files files) {
        map.put(files.getId(), files);
    }

    public Files getOnt(Integer id) {
        return map.get(id);
    }

    public void delete(Integer id) {
        map.remove(id);
    }
}
