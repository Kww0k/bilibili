package com.bilibili.message.component;

import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Silvery
 * @since 2023/9/10 19:04
 */
@ServerEndpoint("/imserver/{username}")
@Component
@Slf4j
public class WebsocketServer {


}
