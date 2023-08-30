package com.bilibili.commons.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.commons.domain.entity.Todo;
import org.apache.ibatis.annotations.Mapper;


/**
 * 代办事项Todo表数据库访问层
 *
 * @author Silvery
 * @since 2023-08-25 09:25:19
 */
@Mapper
public interface TodoMapper extends BaseMapper<Todo> {

}
