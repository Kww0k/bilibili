package com.bilibili.message.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Silvery
 * @since 2023/9/10 19:00
 */
@Configuration
public class WebSocketConfig {

    /**
     * webSocket配置
     *
     * @author Silvery
     * @since 2023/9/10 19:01
     * @return ServerEndpointExporter
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
