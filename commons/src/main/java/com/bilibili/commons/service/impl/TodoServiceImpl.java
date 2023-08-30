package com.bilibili.commons.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.InsertTodoDTO;
import com.bilibili.commons.domain.dto.UpdateTodoDTO;
import com.bilibili.commons.domain.entity.Todo;
import com.bilibili.commons.domain.vo.TodoListVO;
import com.bilibili.commons.exctption.user.TodoNotFindException;
import com.bilibili.commons.mapper.TodoMapper;
import com.bilibili.commons.service.TodoService;
import com.bilibili.commons.utils.BeanCopyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.bilibili.commons.constants.AppConstants.FALSE_CODE;

/**
 * 代办事项Todo表服务实现类
 *
 * @author Silvery
 * @since 2023-08-25 09:25:21
 */
@Service("todoService")
@RequiredArgsConstructor
public class TodoServiceImpl extends ServiceImpl<TodoMapper, Todo> implements TodoService {

    private final BeanCopyUtils beanCopyUtils;

    @Override
    public RestBean<List<TodoListVO>> listTodo(String todoAffair) {
        return RestBean.success(baseMapper.selectList(new LambdaQueryWrapper<Todo>()
                        .like(StringUtils.hasText(todoAffair), Todo::getTodoAffair, todoAffair))
                .stream()
                .map(todo -> beanCopyUtils.copyBean(todo, TodoListVO.class))
                .toList());
    }

    @Override
    public RestBean<Void> insertTodo(InsertTodoDTO insertTodoDTO) {
        baseMapper.insert(beanCopyUtils.copyBean(insertTodoDTO, Todo.class));
        return RestBean.success();
    }

    @Override
    public RestBean<Void> updateTodoById(UpdateTodoDTO updateTodoDTO) {
        if (baseMapper.updateById(beanCopyUtils.copyBean(updateTodoDTO, Todo.class)) == FALSE_CODE)
            throw new TodoNotFindException();
        return RestBean.success();
    }

    @Override
    public RestBean<Void> deleteTodoById(Integer id) {
        if (baseMapper.deleteById(id) == FALSE_CODE)
            throw new TodoNotFindException();
        return RestBean.success();
    }
}
