package com.bilibili.commons.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.bilibili.commons.domain.LoginUser;
import com.bilibili.commons.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.bilibili.commons.constants.AppConstants.AUTH_HEADER;

/**
 * @author Silvery
 * @since 2023/8/17 15:11
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String authorization = request.getHeader(AUTH_HEADER);
        DecodedJWT jwt = jwtUtils.resolveJwt(authorization);
        if (jwt != null) {
            // 解析jwt 把用户信息存入SecurityContextHolder
            LoginUser loginUser = jwtUtils.toUser(jwt);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, null);
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        // 如果token为空则交给security处理
        filterChain.doFilter(request, response);
    }
}
