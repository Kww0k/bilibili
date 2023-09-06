package com.bilibili.commons.cache;

import com.bilibili.commons.domain.entity.Tag;
import com.bilibili.commons.domain.entity.Todo;
import com.bilibili.commons.utils.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.bilibili.commons.constants.AppConstants.TAG_CACHE;
import static com.bilibili.commons.constants.AppConstants.TODO_CACHE;

/**
 * @author Silvery
 * @since 2023/9/4 16:28
 */
@Component
@RequiredArgsConstructor
public class TodoListCache {

    private final RedisCache redisCache;

    public List<Todo> getTodoList() {
        return redisCache.getCacheMap(TODO_CACHE)
                .values()
                .stream()
                .map(Todo.class::cast)
                .toList();
    }

    public void save(Todo todo) {
        redisCache.setCacheMapValue(TODO_CACHE, TODO_CACHE + todo.getId(), todo);
    }

    public Todo getOne(Integer id) {
        return (Todo) redisCache.getCacheMap(TODO_CACHE).get(TODO_CACHE + id);
    }

    public void delete(Integer id) {
        redisCache.delCacheMapValue(TODO_CACHE, TODO_CACHE + id);
    }
}
