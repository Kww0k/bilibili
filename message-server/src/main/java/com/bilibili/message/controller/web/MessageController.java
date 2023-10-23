package com.bilibili.message.controller.web;

import com.bilibili.commons.annotation.SystemLog;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.vo.SendMessageVO;
import com.bilibili.commons.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Silvery
 * @since 2023/9/16 16:27
 */
@RestController
@RequestMapping("/web/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/getMessageByFromAndToId")
    @SystemLog(businessName = "获取历史记录")
    public RestBean<List<SendMessageVO>> getMessageByFromAndToId(Integer from, Integer to) {
        return messageService.getMessageByFromAndToId(from, to);
    }
}
