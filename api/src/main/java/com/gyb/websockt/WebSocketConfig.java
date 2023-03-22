package com.gyb.websockt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @date 2023/3/21 - 19:03
 */


@Configuration
public class WebSocketConfig {
//将ServerEndpointExporter注入到Spring容器
    @Bean
    public ServerEndpointExporter getServerEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
