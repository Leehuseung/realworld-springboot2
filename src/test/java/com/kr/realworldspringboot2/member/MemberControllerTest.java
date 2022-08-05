package com.kr.realworldspringboot2.member;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Test
    @DisplayName("사용자 등록 테스트")
    public void register_email(@Autowired MockMvc mvc) throws Exception {
        //given
        JSONObject body = new JSONObject();
        JSONObject user = new JSONObject();
        user.put("username","Jacob");
        user.put("email","jake@jake.jake");
        user.put("password","1111");
        body.put("user",user);

        //when
        mvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(body.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.username").value("Jacob"))
                .andExpect(jsonPath("$.user.email").value("jake@jake.jake"))
                .andExpect(jsonPath("$.user.token").isNotEmpty())
                .andExpect(jsonPath("$.user.bio").isEmpty())
                .andExpect(jsonPath("$.user.image").isEmpty());
    }


    @Test
    @DisplayName("사용자 이름 중복 등록 테스트")
    public void register_username_duplicate(@Autowired MockMvc mvc) throws Exception {
        JSONObject body = new JSONObject();
        JSONObject user = new JSONObject();
        user.put("username","test01");
        user.put("email","unique_email@test.com");
        user.put("password","1111");
        body.put("user",user);

        //when
        mvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(body.toString()))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    @DisplayName("사용자 이메일 중복 등록 테스트")
    public void register_email_duplicate(@Autowired MockMvc mvc) throws Exception {
        //given
        JSONObject body = new JSONObject();
        JSONObject user = new JSONObject();
        user.put("username","unique");
        user.put("email","test01@realworld.com");
        user.put("password","1111");
        body.put("user",user);

        //when
        mvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(body.toString()))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails("test01@realworld.com")
    @DisplayName("로그인 사용자 정보 get 테스트")
    public void get_current_user(@Autowired MockMvc mvc) throws Exception {
        //when
        mvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.username").value("test01"))
                .andExpect(jsonPath("$.user.email").value("test01@realworld.com"))
                .andExpect(jsonPath("$.user.token").isNotEmpty())
                .andExpect(jsonPath("$.user.bio").isEmpty())
                .andExpect(jsonPath("$.user.image").isEmpty());
    }

}