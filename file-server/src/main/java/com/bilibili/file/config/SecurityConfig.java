package com.bilibili.file.config;

import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.filter.JwtAuthFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

import static com.bilibili.commons.constants.HttpStatus.HTTP_STATUS_401;

/**
 * @author Silvery
 * @since 2023/8/17 13:59
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(conf -> conf
                        .requestMatchers("/web/file/getFileById/**").permitAll()
                        .anyRequest().authenticated())
                // 异常处理
                .exceptionHandling(conf -> conf
                        .authenticationEntryPoint(this::commence))
                // 关闭csrf
                .csrf(AbstractHttpConfigurer::disable)
                // 把session设置为无状态
                .sessionManagement(conf -> conf
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 注册过滤器在认证器前
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        response.getWriter().write(RestBean.failure(HTTP_STATUS_401, authException.getMessage()).toJsonString());
    }


}
