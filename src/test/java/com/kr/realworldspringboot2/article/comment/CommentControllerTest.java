package com.kr.realworldspringboot2.article.comment;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CommentControllerTest {

    @Test
    @WithUserDetails("test01@realworld.com")
    @DisplayName("코멘트 등록 테스트")
    public void add_comment(@Autowired MockMvc mvc) throws Exception {
        JSONObject body = new JSONObject();
        JSONObject comment = new JSONObject();
        comment.put("body","comment add test");
        body.put("comment",comment);

        mvc.perform(post("/api/articles/how-to-train-your-dragon/comments").contentType(MediaType.APPLICATION_JSON).content(body.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comment.id").isNotEmpty())
                .andExpect(jsonPath("$.comment.body").value("comment add test"))
                .andExpect(jsonPath("$.comment.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.comment.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.comment.author.username").value("test01"))
                .andExpect(jsonPath("$.comment.author.following").value(false))
                .andExpect(jsonPath("$.comment.author.bio").hasJsonPath())
                .andExpect(jsonPath("$.comment.author.image").hasJsonPath());
    }
}