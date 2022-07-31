package com.kr.realworldspringboot2.security;

import com.kr.realworldspringboot2.util.JWTUtil;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {
    private JWTUtil jwtUtil;
    public ApiLoginFilter(String defaultFilterProcessUrl, JWTUtil jwtUtil) {
        super(defaultFilterProcessUrl);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String body = request.getReader().lines().collect(Collectors.joining());

        if(body.equals("")){
            throw new BadCredentialsException("body cannot be null");
        }

        JSONObject jo = (JSONObject) JSONValue.parse(body);
        JSONObject user = (JSONObject) jo.get("user");


        String email = user.getAsString("email");
        String pw = user.getAsString("password");

        if(email == null || email.equals("")){
            throw new BadCredentialsException("email cannot be null");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email,pw);

        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        JSONObject jo = new JSONObject();
        JSONObject user = new JSONObject();

        AuthMemberDTO memberDTO = (AuthMemberDTO)authResult.getPrincipal();

        String token = jwtUtil.generateToken(authResult);

        user.put("token",token);
        user.put("email",memberDTO.getEmail());
        user.put("username",memberDTO.getUsername());
        user.put("bio",memberDTO.getBio());
        user.put("image",memberDTO.getImage());
        jo.put("user",user);

        response.setContentType("application/json");
        response.getOutputStream().write(jo.toString().getBytes());
    }
}
