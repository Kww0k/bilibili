package com.bilibili.commons.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.commons.cache.AccountListCache;
import com.bilibili.commons.cache.MessageCache;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.entity.Message;
import com.bilibili.commons.domain.vo.SendMessageVO;
import com.bilibili.commons.exctption.auth.UserNotFindException;
import com.bilibili.commons.mapper.MessageMapper;
import com.bilibili.commons.service.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * (Message)表服务实现类
 *
 * @author Silvery
 * @since 2023-09-13 23:14:01
 */
@Service("messageService")
@RequiredArgsConstructor
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    private final MessageCache messageCache;

    private final AccountListCache accountListCache;

    @Override
    public RestBean<List<SendMessageVO>> getMessageByFromAndToId(Integer from, Integer to) {
        return RestBean.success(messageCache.getList()
                .stream()
                .filter(message -> (Objects.equals(from, message.getFromId()) && Objects.equals(to, message.getToId())) ||
                        (Objects.equals(from, message.getToId()) && Objects.equals(to, message.getFromId())))
                .sorted(Comparator.comparing(Message::getCreateTime))
                .map(message -> new SendMessageVO().setFrom(message.getFromId()).setTo(message.getToId()).setText(message.getContent()))
                .toList());
    }

    @Override
    public void insertNewMessage(Integer id, Integer to, String text) {
        if (accountListCache.getOne(id) == null || accountListCache.getOne(to) == null)
            throw new UserNotFindException();
        Message message = new Message().setToId(to).setFromId(id).setContent(text);
        baseMapper.insert(message);
        messageCache.save(baseMapper.selectById(message.getId()));
    }
}
