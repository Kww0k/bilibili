package com.bilibili.user.controller.admin;

import com.bilibili.commons.annotation.SystemLog;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.dto.InsertTodoDTO;
import com.bilibili.commons.domain.dto.UpdateTodoDTO;
import com.bilibili.commons.domain.vo.TodoListVO;
import com.bilibili.commons.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Silvery
 * @since 2023/8/25 09:29
 */
@RestController
@RequestMapping("/admin/todo")
@RequiredArgsConstructor
@Validated
public class AdminTodoController {

    private final TodoService todoService;

    @SystemLog(businessName = "获取事物列表")
    @GetMapping("/listTodo")
    public RestBean<List<TodoListVO>> listTodo(String todoAffair) {
        return todoService.listTodo(todoAffair);
    }

    @SystemLog(businessName = "新增事物")
    @PostMapping("/insertTodo")
    public RestBean<Void> insertTodo(@Valid @RequestBody InsertTodoDTO insertTodoDTO) {
        return todoService.insertTodo(insertTodoDTO);
    }

    @SystemLog(businessName = "更新事物")
    @PutMapping("/updateTodoById")
    public RestBean<Void> updateTodoById(@Valid @RequestBody UpdateTodoDTO updateTodoDTO) {
        return todoService.updateTodoById(updateTodoDTO);
    }

    @SystemLog(businessName = "删除事物")
    @DeleteMapping("/deleteTodoById/{id}")
    public RestBean<Void> deleteTodoById(@PathVariable Integer id) {
        return todoService.deleteTodoById(id);
    }
}
