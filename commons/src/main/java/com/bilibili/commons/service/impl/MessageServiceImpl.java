package com.bilibili.commons.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.commons.domain.entity.Message;
import com.bilibili.commons.mapper.MessageMapper;
import com.bilibili.commons.service.MessageService;
import org.springframework.stereotype.Service;

/**
 * (Message)表服务实现类
 *
 * @author Silvery
 * @since 2023-09-13 23:14:01
 */
@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

}
