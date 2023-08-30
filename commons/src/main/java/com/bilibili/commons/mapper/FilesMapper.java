package com.bilibili.commons.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.commons.domain.entity.Files;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Files)表数据库访问层
 *
 * @author Silvery
 * @since 2023-08-21 10:49:46
 */
@Mapper
public interface FilesMapper extends BaseMapper<Files> {

}
