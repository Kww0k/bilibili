package com.bilibili.commons.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.InsertTodoDTO;
import com.bilibili.commons.domain.dto.UpdateTodoDTO;
import com.bilibili.commons.domain.entity.Todo;
import com.bilibili.commons.domain.vo.TodoListVO;

import java.util.List;


/**
 * 代办事项Todo表服务接口
 *
 * @author Silvery
 * @since 2023-08-25 09:25:21
 */
public interface TodoService extends IService<Todo> {

    /**
     * 获取代办事项列表
     *
     * @author Silvery
     * @since 2023/8/25 09:38
     * @param todoAffair 时间名
     * @return RestBean<List<TodoListVO>>
     */
    RestBean<List<TodoListVO>> listTodo(String todoAffair);

    /**
     * 插入事项
     *
     * @author Silvery
     * @since 2023/8/25 10:09
     * @param insertTodoDTO 插入的信息
     * @return RestBean<Void>
     */
    RestBean<Void> insertTodo(InsertTodoDTO insertTodoDTO);

    /**
     * 更新事物
     *
     * @author Silvery
     * @since 2023/8/25 10:09
     * @param updateTodoDTO 更新的信息
     * @return RestBean<Void>
     */
    RestBean<Void> updateTodoById(UpdateTodoDTO updateTodoDTO);

    /**
     * 删除事物
     *
     * @author Silvery
     * @since 2023/8/25 10:09
     * @param id 要删除的事物id
     * @return RestBean<Void>
     */
    RestBean<Void> deleteTodoById(Integer id);
}
