package com.bilibili.commons.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.commons.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Tag)表数据库访问层
 *
 * @author Silvery
 * @since 2023-08-22 15:43:06
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}
