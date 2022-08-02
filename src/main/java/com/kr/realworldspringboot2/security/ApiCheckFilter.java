package com.kr.realworldspringboot2.security;

import com.kr.realworldspringboot2.util.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiCheckFilter extends OncePerRequestFilter {
    private JWTUtil jwtUtil;
    public ApiCheckFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @Value("${jwt.start}")
    String TOKEN_START;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(hasAuthHeader(request)){
            String authHeader = request.getHeader("Authorization");
            Authentication authentication = jwtUtil.getAuthentication(authHeader.substring(authHeader.indexOf(" ")+1));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }

    private boolean hasAuthHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if(StringUtils.hasText(authHeader) && authHeader.startsWith(TOKEN_START + " ")){
            return true;
        }
        return false;
    }

}
