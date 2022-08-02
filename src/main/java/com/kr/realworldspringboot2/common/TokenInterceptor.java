package com.kr.realworldspringboot2.common;

import com.kr.realworldspringboot2.member.MemberRepository;
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
    private final MemberRepository memberRepository;

    public TokenInterceptor(JWTUtil jwtUtil, MemberRepository memberRepository) {
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getHeader("authorization") != null){
            String tokenStr = jwtUtil.getToken(request.getHeader("authorization"));
            Authentication authentication = jwtUtil.getAuthentication(tokenStr);
            long id = memberRepository.findByEmail(authentication.getName()).get().getId();
            request.setAttribute("authentication",authentication);
            request.setAttribute("id",id);
        } else {
            request.setAttribute("authentication",new UsernamePasswordAuthenticationToken(null,null));
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
