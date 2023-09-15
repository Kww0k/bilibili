package com.bilibili.message.server;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.vo.AccountMessageVO;
import com.bilibili.commons.domain.vo.SendMessageVO;
import com.bilibili.commons.service.AccountService;
import com.bilibili.commons.service.service.MessageService;
import com.bilibili.commons.utils.SpringContext;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author websocket服务
 */
@Component
@ServerEndpoint(value = "/imserver/{id}")
@Slf4j
public class WebSocketServer {

    MessageService messageService = (MessageService) SpringContext.getBean("messageService");

    AccountService accountService = (AccountService) SpringContext.getBean("accountService");

    /**
     * 记录当前在线连接数
     */
    public static final Map<Integer, List<Session>> sessionMap = new ConcurrentHashMap<>();


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("id") Integer id) {
        List<AccountMessageVO> accountMessageVOS = accountService.getAccountMessageListById(id);
        List<Session> list = sessionMap.get(id);
        if (list == null || list.isEmpty()) {
            List<Session> sessions = new ArrayList<>();
            sessions.add(session);
            sessionMap.put(id, sessions);
        } else {
            list.add(session);
            sessionMap.put(id, list);
        }
        sendAllMessage(id, RestBean.success(accountMessageVOS)
                .toJsonString());  // 后台发送消息给所有的客户端
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("id") Integer id) {
        List<Session> sessions = sessionMap.get(id);
        if (sessions != null && !sessions.isEmpty()) {
            sessions.remove(session);
            log.info("已从id={}的用户中移除session，当前在线人数为：{}", id, sessions.size());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("id") Integer id) {
        log.info("服务端收到用户id={}的消息:{}", id, message);
        JSONObject obj = JSONUtil.parseObj(message);
        String toUsername = obj.getStr("to"); // to表示发送给哪个用户，比如 admin
        String text = obj.getStr("text"); // 发送的消息文本  hello
        List<Session> list = sessionMap.get(id);// 根据 to用户名来获取 session，再通过session发送消息文本
        if (list != null && !list.isEmpty()) {
            list.forEach(toSession ->
                    this.sendMessage(
                            RestBean.successMessage(new SendMessageVO().setId(id).setText(text)).toJsonString(), toSession));
        } else {
            log.info("发送失败，未找到用户username={}的session", toUsername);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }

    /**
     * 服务端发送消息给所有客户端
     */
    private void sendAllMessage(Integer id, String message) {
        try {
            for (Session session : sessionMap.get(id)) {
                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }
}
