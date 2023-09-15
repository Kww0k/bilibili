package com.bilibili.commons.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.commons.domain.entity.Likes;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Likes)表数据库访问层
 *
 * @author Silvery
 * @since 2023-09-15 16:01:36
 */
@Mapper
public interface LikesMapper extends BaseMapper<Likes> {

}
