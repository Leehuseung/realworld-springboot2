package com.kr.realworldspringboot2.config;

import com.kr.realworldspringboot2.member.Member;
import com.kr.realworldspringboot2.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void default_delete() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void login_success_test(@Autowired MockMvc mvc) throws Exception {
        //given
        Member test01 = Member.builder()
                .email("test01@realworld.com")
                .username("test01")
                .password(passwordEncoder.encode("1111"))
                .build();
        memberRepository.save(test01);


        String body = "{\"user\":{\"email\":\"test01@realworld.com\",\"password\":\"1111\"}}";

        //when
        mvc.perform(post("/api/users/login").contentType(MediaType.APPLICATION_JSON).content(body))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.email").value("test01@realworld.com"))
                .andExpect(jsonPath("$.user.username").value("test01"))
                .andExpect(jsonPath("$.user.token").hasJsonPath());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void login_fail_test(@Autowired MockMvc mvc) throws Exception {
        //given
        Member test01 = Member.builder()
                .email("test01@realworld.com")
                .username("test01")
                .password(passwordEncoder.encode("1111"))
                .build();
        memberRepository.save(test01);

        //when
        String body = "{\"user\":{\"email\":\"noId@realworld.com\",\"password\":\"1111\"}}";

        //then
        mvc.perform(post("/api/users/login").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @DisplayName("사용자 헤더 존재 안할 때 테스트")
    public void none_token_header(@Autowired MockMvc mvc) throws Exception {
        //then
        mvc.perform(post("/api/users/login"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("사용자 이메일 존재 안할 때 테스트")
    public void none_email_header(@Autowired MockMvc mvc) throws Exception {
        //when
        String body = "{\"user\":{\"email\":,\"password\":\"1111\"}}";

        //then
        mvc.perform(post("/api/users/login").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isUnauthorized());
    }

}