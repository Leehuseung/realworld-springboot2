package com.kr.realworldspringboot2.profile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProfileControllerTest {
    
    @Test
    @DisplayName("로그인 없이 프로필 조회 테스트")
    public void get_profile(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/api/profiles/test01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profile.username").value("test01"))
                .andExpect(jsonPath("$.profile.bio").isEmpty())
                .andExpect(jsonPath("$.profile.image").isEmpty())
                .andExpect(jsonPath("$.profile.following").value(false));
    }

    @Test
    @WithUserDetails("test01@realworld.com")
    @DisplayName("Follow 한 프로필 조회 테스트")
    public void follow_profile(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/api/profiles/test02"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profile.username").value("test02"))
                .andExpect(jsonPath("$.profile.bio").isEmpty())
                .andExpect(jsonPath("$.profile.image").isEmpty())
                .andExpect(jsonPath("$.profile.following").value(true));
    }

    @Test
    @WithUserDetails("test01@realworld.com")
    @DisplayName("Follow 안한 프로필 조회 테스트")
    public void unfollow_profile(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/api/profiles/test05"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profile.username").value("test05"))
                .andExpect(jsonPath("$.profile.bio").isEmpty())
                .andExpect(jsonPath("$.profile.image").isEmpty())
                .andExpect(jsonPath("$.profile.following").value(false));
    }
}