package com.kr.realworldspringboot2.config;

import com.kr.realworldspringboot2.member.MemberRepository;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SecurityConfigTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("로그인 성공 테스트")
    public void login_success_test(@Autowired MockMvc mvc) throws Exception {
        JSONObject body = new JSONObject();
        JSONObject user = new JSONObject();
        user.put("email","test01@realworld.com");
        user.put("password","1111");
        body.put("user",user);

        mvc.perform(post("/api/users/login").contentType(MediaType.APPLICATION_JSON).content(body.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.email").value("test01@realworld.com"))
                .andExpect(jsonPath("$.user.username").value("test01"))
                .andExpect(jsonPath("$.user.token").hasJsonPath());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void login_fail_test(@Autowired MockMvc mvc) throws Exception {
        JSONObject body = new JSONObject();
        JSONObject user = new JSONObject();
        user.put("email","noId@realworld.com");
        user.put("password","1111");
        body.put("user",user);

        mvc.perform(post("/api/users/login").contentType(MediaType.APPLICATION_JSON).content(body.toString()))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @DisplayName("사용자 헤더 존재 안할 때 테스트")
    public void none_token_header(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(post("/api/users/login"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("사용자 이메일 존재 안할 때 테스트")
    public void none_email_header(@Autowired MockMvc mvc) throws Exception {
        JSONObject body = new JSONObject();
        JSONObject user = new JSONObject();
        user.put("email",null);
        user.put("password","1111");
        body.put("user",user);

        mvc.perform(post("/api/users/login").contentType(MediaType.APPLICATION_JSON).content(body.toString()))
                .andExpect(status().isUnauthorized());
    }

}