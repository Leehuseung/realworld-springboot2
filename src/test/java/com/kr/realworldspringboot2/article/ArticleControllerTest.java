package com.kr.realworldspringboot2.article;

import net.minidev.json.JSONArray;
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
class ArticleControllerTest {

    @Test
    @WithUserDetails("test01@realworld.com")
    @DisplayName("글등록 테스트")
    public void register_article(@Autowired MockMvc mvc) throws Exception {
        //given
        JSONObject body = new JSONObject();
        JSONObject article = new JSONObject();
        JSONArray tags = new JSONArray();
        article.put("title","register test");
        article.put("description","test description");
        article.put("body","test body");
        tags.add("newTag1");
        tags.add("newTag2");
        tags.add("newTag3");
        article.put("tagList",tags);
        body.put("article",article);

        mvc.perform(post("/api/articles").contentType(MediaType.APPLICATION_JSON).content(body.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.article.slug").value("register-test"))
                .andExpect(jsonPath("$.article.title").value("register test"))
                .andExpect(jsonPath("$.article.description").value("test description"))
                .andExpect(jsonPath("$.article.body").value("test body"))
                .andExpect(jsonPath("$.article.tagList[0]").value("newTag1"))
                .andExpect(jsonPath("$.article.tagList[1]").value("newTag2"))
                .andExpect(jsonPath("$.article.tagList[2]").value("newTag3"))
                .andExpect(jsonPath("$.article.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.article.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.article.favorited").value(false))
                .andExpect(jsonPath("$.article.favoritesCount").value(0))
                .andExpect(jsonPath("$.article.author.username").value("test01"))
                .andExpect(jsonPath("$.article.author.following").value(false))
                .andExpect(jsonPath("$.article.author.bio").hasJsonPath())
                .andExpect(jsonPath("$.article.author.image").hasJsonPath());
    }

    @Test
    @WithUserDetails("test01@realworld.com")
    @DisplayName("이미 존재하는 태그 존재할 때 글등록 테스트")
    public void register_article_already_tags(@Autowired MockMvc mvc) throws Exception {
        //given
        JSONObject body = new JSONObject();
        JSONObject article = new JSONObject();
        JSONArray tags = new JSONArray();
        article.put("title","register test");
        article.put("description","test description");
        article.put("body","test body");
        tags.add("reactjs");
        tags.add("angularjs");
        tags.add("dragons");
        article.put("tagList",tags);
        body.put("article",article);

        mvc.perform(post("/api/articles").contentType(MediaType.APPLICATION_JSON).content(body.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.article.slug").value("register-test"))
                .andExpect(jsonPath("$.article.title").value("register test"))
                .andExpect(jsonPath("$.article.description").value("test description"))
                .andExpect(jsonPath("$.article.body").value("test body"))
                .andExpect(jsonPath("$.article.tagList[0]").value("reactjs"))
                .andExpect(jsonPath("$.article.tagList[1]").value("angularjs"))
                .andExpect(jsonPath("$.article.tagList[2]").value("dragons"))
                .andExpect(jsonPath("$.article.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.article.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.article.favorited").value(false))
                .andExpect(jsonPath("$.article.favoritesCount").value(0))
                .andExpect(jsonPath("$.article.author.username").value("test01"))
                .andExpect(jsonPath("$.article.author.following").value(false))
                .andExpect(jsonPath("$.article.author.bio").hasJsonPath())
                .andExpect(jsonPath("$.article.author.image").hasJsonPath());
    }

}