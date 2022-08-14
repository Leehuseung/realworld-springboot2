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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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


    @Test
    @DisplayName("로그인없이 사용자 글 조회 테스트")
    public void get_article(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/api/articles/how-to-train-your-dragon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.article.slug").value("how-to-train-your-dragon"))
                .andExpect(jsonPath("$.article.title").value("How to train your dragon"))
                .andExpect(jsonPath("$.article.description").value("Ever wonder how?"))
                .andExpect(jsonPath("$.article.body").value("You have to believe"))
                .andExpect(jsonPath("$.article.tagList[0]").value("reactjs"))
                .andExpect(jsonPath("$.article.tagList[1]").value("angularjs"))
                .andExpect(jsonPath("$.article.tagList[2]").value("dragons"))
                .andExpect(jsonPath("$.article.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.article.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.article.favorited").value(false))
                .andExpect(jsonPath("$.article.author.username").value("test01"))
                .andExpect(jsonPath("$.article.author.following").value(false))
                .andExpect(jsonPath("$.article.author.bio").hasJsonPath())
                .andExpect(jsonPath("$.article.author.image").hasJsonPath());
    }


    @Test
    @DisplayName("글 좋아요 count test")
    public void get_article_favorite_count(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/api/articles/how-to-train-your-dragon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.article.favoritesCount").value(2));
    }

    @Test
    @WithUserDetails("test02@realworld.com")
    @DisplayName("로그인 사용자가 좋아요한 글 조회 테스트")
    public void get_article_favorite_true(@Autowired MockMvc mvc) throws Exception {
        //test02가 how-to-train-your-dragon를 좋아요
        mvc.perform(get("/api/articles/how-to-train-your-dragon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.article.favorited").value(true));
    }

    @Test
    @WithUserDetails("test06@realworld.com")
    @DisplayName("로그인 사용자가 좋아요안한 글 조회 테스트")
    public void get_article_favorite_false(@Autowired MockMvc mvc) throws Exception {
        //test06이 how-to-train-your-dragon를 좋아요 안함
        mvc.perform(get("/api/articles/how-to-train-your-dragon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.article.favorited").value(false));
    }

    @Test
    @WithUserDetails("test05@realworld.com")
    @DisplayName("로그인 사용자가 Follow 한 author 테스트")
    public void get_article_following_true(@Autowired MockMvc mvc) throws Exception {
        //test05는 test01(글작성자)를 follow
        mvc.perform(get("/api/articles/how-to-train-your-dragon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.article.author.following").value(true));
    }

    @Test
    @WithUserDetails("test04@realworld.com")
    @DisplayName("로그인 사용자가 Follow 안한 author 테스트")
    public void get_article_following_false(@Autowired MockMvc mvc) throws Exception {
        //test04는 test01(글작성자)를 follow안함
        mvc.perform(get("/api/articles/how-to-train-your-dragon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.article.author.following").value(false));
    }


    @Test
    @WithUserDetails("test01@realworld.com")
    @DisplayName("글 삭제 테스트")
    public void delete_article(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(delete("/api/articles/how-to-train-your-dragon"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("test01@realworld.com")
    @DisplayName("글 수정 테스트")
    public void update_article(@Autowired MockMvc mvc) throws Exception {
        //given
        JSONObject body = new JSONObject();
        JSONObject article = new JSONObject();
        JSONArray tags = new JSONArray();
        article.put("title","update test");
        article.put("description","test description update");
        article.put("body","test body update");
        tags.add("updateTag1");
        tags.add("updateTag2");
        article.put("tagList",tags);
        body.put("article",article);

        mvc.perform(put("/api/articles/how-to-train-your-dragon").contentType(MediaType.APPLICATION_JSON).content(body.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.article.slug").value("update-test"))
                .andExpect(jsonPath("$.article.title").value("update test"))
                .andExpect(jsonPath("$.article.description").value("test description update"))
                .andExpect(jsonPath("$.article.body").value("test body update"))
                .andExpect(jsonPath("$.article.tagList[0]").value("updateTag1"))
                .andExpect(jsonPath("$.article.tagList[1]").value("updateTag2"))
                .andExpect(jsonPath("$.article.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.article.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.article.favorited").value(true))
                .andExpect(jsonPath("$.article.favoritesCount").value(2))
                .andExpect(jsonPath("$.article.author.username").value("test01"))
                .andExpect(jsonPath("$.article.author.following").value(false))
                .andExpect(jsonPath("$.article.author.bio").hasJsonPath())
                .andExpect(jsonPath("$.article.author.image").hasJsonPath());
    }

    @Test
    @WithUserDetails("test01@realworld.com")
    @DisplayName("글 태그 수정 테스트")
    public void update_tags_article(@Autowired MockMvc mvc) throws Exception {
        //given
        JSONObject body = new JSONObject();
        JSONObject article = new JSONObject();
        JSONArray tags = new JSONArray();
        tags.add("reactjs");
        tags.add("updateTag1");
        article.put("tagList",tags);
        body.put("article",article);

        mvc.perform(put("/api/articles/how-to-train-your-dragon").contentType(MediaType.APPLICATION_JSON).content(body.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.article.tagList[0]").value("reactjs"))
                .andExpect(jsonPath("$.article.tagList[1]").value("updateTag1"));
    }

    @Test
    @WithUserDetails("test01@realworld.com")
    @DisplayName("글 태그 삭제 수정")
    public void update_tags_not_change_article(@Autowired MockMvc mvc) throws Exception {
        //given
        JSONObject body = new JSONObject();
        JSONObject article = new JSONObject();
        JSONArray tags = new JSONArray();
        article.put("title","update test");
        article.put("description","test description update");
        article.put("body","test body update");
        article.put("tagList",tags);
        body.put("article",article);

        mvc.perform(put("/api/articles/how-to-train-your-dragon").contentType(MediaType.APPLICATION_JSON).content(body.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.article.tagList").isEmpty());
    }

}