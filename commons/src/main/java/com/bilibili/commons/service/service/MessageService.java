package com.bilibili.commons.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.entity.Message;
import com.bilibili.commons.domain.vo.SendMessageVO;

import java.util.List;


/**
 * (Message)表服务接口
 *
 * @author Silvery
 * @since 2023-09-13 23:14:01
 */
public interface MessageService extends IService<Message> {

    /**
     * 获取历史记录
     *
     * @author Silvery
     * @since 2023/9/16 16:30
     * @param from 当前用户
     * @param to 对话用户
     * @return RestBean
     */
    RestBean<List<SendMessageVO>> getMessageByFromAndToId(Integer from, Integer to);
}
