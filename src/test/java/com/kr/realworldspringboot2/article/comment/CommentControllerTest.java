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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CommentControllerTest {

    @Test
    @DisplayName("댓글 조회 테스트")
    public void get_comments(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/api/articles/how-to-train-your-dragon/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments[0].id").value(5))
                .andExpect(jsonPath("$.comments[1].id").value(4))
                .andExpect(jsonPath("$.comments[2].id").value(3))
                .andExpect(jsonPath("$.comments[3].id").value(2))
                .andExpect(jsonPath("$.comments[4].id").value(1))
                .andExpect(jsonPath("$.comments[0].body").value("comment test by member 5"))
                .andExpect(jsonPath("$.comments[1].body").value("comment test by member 4"))
                .andExpect(jsonPath("$.comments[2].body").value("comment test by member 3"))
                .andExpect(jsonPath("$.comments[3].body").value("comment test by member 2"))
                .andExpect(jsonPath("$.comments[4].body").value("comment test by member 1"))
                .andExpect(jsonPath("$.comments[0].author.username").value("test05"))
                .andExpect(jsonPath("$.comments[1].author.username").value("test04"))
                .andExpect(jsonPath("$.comments[2].author.username").value("test03"))
                .andExpect(jsonPath("$.comments[3].author.username").value("test02"))
                .andExpect(jsonPath("$.comments[4].author.username").value("test01"))
                .andExpect(jsonPath("$.comments[0].author.bio").hasJsonPath())
                .andExpect(jsonPath("$.comments[0].author.image").hasJsonPath())
                .andExpect(jsonPath("$.comments[0].author.following").value(false))
                .andExpect(jsonPath("$.comments[1].author.following").value(false))
                .andExpect(jsonPath("$.comments[2].author.following").value(false))
                .andExpect(jsonPath("$.comments[3].author.following").value(false))
                .andExpect(jsonPath("$.comments[4].author.following").value(false));
    }

    @Test
    @WithUserDetails("test01@realworld.com")
    @DisplayName("조회한 댓글의 author 의 following 여부")
    public void get_comments_following(@Autowired MockMvc mvc) throws Exception {
        //test01은 test02를 follow
        mvc.perform(get("/api/articles/how-to-train-your-dragon/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments[0].author.following").value(false))
                .andExpect(jsonPath("$.comments[1].author.following").value(false))
                .andExpect(jsonPath("$.comments[2].author.following").value(false))
                .andExpect(jsonPath("$.comments[3].author.username").value("test02"))
                .andExpect(jsonPath("$.comments[3].author.following").value(true))
                .andExpect(jsonPath("$.comments[4].author.following").value(false));
    }

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