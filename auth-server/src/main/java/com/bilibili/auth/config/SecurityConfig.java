package com.bilibili.auth.config;

import com.bilibili.commons.domain.LoginUser;
import com.bilibili.commons.domain.RestBean;
import com.bilibili.commons.domain.vo.AccountAuthVO;
import com.bilibili.commons.domain.vo.AuthVO;
import com.bilibili.commons.filter.JwtAuthFilter;
import com.bilibili.commons.utils.BeanCopyUtils;
import com.bilibili.commons.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

import static com.bilibili.commons.constants.AppConstants.AUTH_HEADER;
import static com.bilibili.commons.constants.HttpStatus.HTTP_STATUS_401;
import static com.bilibili.commons.enums.HttpMessage.LOGOUT_ERROR;

/**
 * @author Silvery
 * @since 2023/8/17 13:59
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtils jwtUtils;

    private final JwtAuthFilter jwtAuthFilter;

    private final BeanCopyUtils beanCopyUtils;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 设置那些请求放行，哪些需要认证
                .authorizeHttpRequests(conf -> conf
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/web/**").permitAll()
                        .anyRequest().authenticated())
                // 表单登陆相关配置
                .formLogin(conf -> conf
                        .loginProcessingUrl("/login")
                        .successHandler(this::onAuthenticationSuccess)
                        .failureHandler(this::onAuthenticationFailure))
                // 退出登陆相关配置
                .logout(conf -> conf.logoutUrl("/logout")
                        .logoutSuccessHandler(this::onLogoutSuccess))
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

    /**
     * 异常处理
     *
     * @author Silvery
     * @since 2023/8/18 11:55
     * @param request 请求
     * @param response 响应
     * @param authException 发生的错误
     */
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        response.getWriter().write(RestBean.failure(HTTP_STATUS_401, authException.getMessage()).toJsonString());
    }

    /**
     * 登陆的逻辑
     *
     * @author Silvery
     * @since 2023/8/18 11:56
     * @param request 请求
     * @param response 响应
     * @param authentication 认证信息
     */
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        // 获取登陆的用户信息
        LoginUser user = (LoginUser) authentication.getPrincipal();

        // 生成jwt
        String token = jwtUtils.createJwt(user);
        AccountAuthVO accountAuthVO = beanCopyUtils.copyBean(user.getAccount(), AccountAuthVO.class);
        AuthVO authVO = new AuthVO(accountAuthVO, token);

        // 返回
        response.getWriter().write(RestBean.success(authVO).toJsonString());
    }

    /**
     * 登陆失败的处理
     *
     * @author Silvery
     * @since 2023/8/18 11:56
     * @param request 请求
     * @param response 响应
     * @param exception 发生的错误
     */
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(RestBean.failure(HTTP_STATUS_401, exception.getMessage()).toJsonString());
    }

    /**
     * 退出登陆做的操作
     *
     * @author Silvery
     * @since 2023/8/18 11:57
     * @param request 请求
     * @param response 响应
     * @param authentication 认证信息
     */
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        // 获取token
        String authorization = request.getHeader(AUTH_HEADER);
        if (jwtUtils.invalidateJwt(authorization))
            response.getWriter().write(RestBean.success().toJsonString());
        else
            response.getWriter().write(RestBean.failure(LOGOUT_ERROR).toJsonString());
    }
}
