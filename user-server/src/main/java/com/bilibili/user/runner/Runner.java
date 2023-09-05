package com.bilibili.user.runner;

import com.bilibili.commons.cache.TodoListCache;
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

    private final TodoMapper todoMapper;

    @Override
    public void run(String... args) {
        todoMapper.selectList(null).forEach(todoListCache::save);
    }
}
