package com.bilibili.commons.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.bilibili.commons.utils.SecurityUtil;
import com.bilibili.commons.utils.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 更新时间和创建时间
 *
 * @author Silvery
 * @since 2023/8/1 13:43
 */
@Component
@RequiredArgsConstructor
public class MyMetaObjectHandler implements MetaObjectHandler {

    private final SecurityUtil securityUtil;

    private final TimeUtil timeUtil;

    @Override
    public void insertFill(MetaObject metaObject) {

        this.setFieldValByName("createTime", timeUtil.getLocalTime(), metaObject);
        this.setFieldValByName("createBy", getUserId() , metaObject);
        this.setFieldValByName("updateTime", timeUtil.getLocalTime(), metaObject);
        this.setFieldValByName("updateBy", getUserId(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", timeUtil.getLocalTime(), metaObject);
        this.setFieldValByName("updateBy", getUserId(), metaObject);
    }

    private String getUserId() {
        Integer userId;
        try {
            // 这里会有一个用户不存在异常,需要捕获
            userId = securityUtil.getUserId();
        } catch (Exception e) {
            // 这是插入或者更新时自动插入，所以不用返回和处理这个异常，如果有这个异常，则将id设置为-1
            userId = -1;
        }
        return userId.toString();
    }

}