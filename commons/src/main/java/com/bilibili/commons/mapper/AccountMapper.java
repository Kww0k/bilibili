package com.bilibili.commons.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bilibili.commons.domain.entity.Account;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Account)表数据库访问层
 *
 * @author Silvery
 * @since 2023-08-17 15:40:05
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {

}
