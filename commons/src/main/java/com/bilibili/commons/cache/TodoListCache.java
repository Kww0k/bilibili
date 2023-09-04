package com.bilibili.commons.cache;

import com.bilibili.commons.domain.entity.Account;
import com.bilibili.commons.domain.entity.Todo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Silvery
 * @since 2023/9/4 16:28
 */
@Component
public class TodoListCache {

    private final ConcurrentHashMap<Integer, Todo> todoMap = new ConcurrentHashMap<>();

    public ConcurrentHashMap<Integer, Todo> getTodoMap() {
        return todoMap;
    }

    public List<Todo> getTodoList() {
        return todoMap.values().stream().toList();
    }

    public void save(Todo todo) {
        todoMap.put(todo.getId(), todo);
    }

    public void delete(Integer id) {
        todoMap.remove(id);
    }
}
