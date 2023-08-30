package com.bilibili.commons.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.commons.domain.entity.Video;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Video)表数据库访问层
 *
 * @author Silvery
 * @since 2023-08-25 15:23:43
 */
@Mapper
public interface VideoMapper extends BaseMapper<Video> {

}
