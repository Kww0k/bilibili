package com.bilibili.commons.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.commons.domain.entity.Message;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Message)表数据库访问层
 *
 * @author Silvery
 * @since 2023-09-13 23:13:59
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}
