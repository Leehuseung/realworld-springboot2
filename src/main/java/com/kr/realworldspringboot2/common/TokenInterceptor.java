package com.kr.realworldspringboot2.common;

import com.kr.realworldspringboot2.util.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final JWTUtil jwtUtil;

    public TokenInterceptor(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getHeader("authorization") != null){
            String tokenStr = jwtUtil.getToken(request.getHeader("authorization"));
            Authentication authentication = jwtUtil.getAuthentication(tokenStr);
            request.setAttribute("authentication",authentication);
        } else {
            request.setAttribute("authentication",new UsernamePasswordAuthenticationToken(null,null));
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
