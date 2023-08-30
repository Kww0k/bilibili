package com.bilibili.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

/**
 * @author Silvery
 * @since 2023/8/24 10:32
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public SecurityConfiguration securityConfiguration(){
        return SecurityConfigurationBuilder.builder().build();
    }

    @Bean
    public UiConfiguration uiConfiguration(){
        return UiConfigurationBuilder.builder().build();
    }

}