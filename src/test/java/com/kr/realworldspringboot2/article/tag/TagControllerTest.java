package com.kr.realworldspringboot2.article.tag;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TagControllerTest {

    @Test
    @DisplayName("태그 조회 테스트")
    public void unFavorite_article_none(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tags[0]").value("reactjs"))
                .andExpect(jsonPath("$.tags[1]").value("angularjs"))
                .andExpect(jsonPath("$.tags[2]").value("dragons"));
    }

}