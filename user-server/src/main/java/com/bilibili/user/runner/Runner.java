package com.bilibili.user.runner;

import com.bilibili.commons.cache.LikesCache;
import com.bilibili.commons.cache.TodoListCache;
import com.bilibili.commons.mapper.LikesMapper;
import com.bilibili.commons.mapper.TodoMapper;
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

    private final TodoListCache todoListCache;

    private final LikesMapper likesMapper;

    private final LikesCache likesCache;

    private final TodoMapper todoMapper;

    @Override
    public void run(String... args) {
        if (todoListCache.getTodoList() == null || todoListCache.getTodoList().isEmpty())
            todoMapper.selectList(null).forEach(todoListCache::save);
        if (likesCache.getList() == null || likesCache.getList().isEmpty())
            likesMapper.selectList(null).forEach(likesCache::save);
    }
}
