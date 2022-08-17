package com.kr.realworldspringboot2.article;

import com.kr.realworldspringboot2.security.AuthMemberDTO;
import net.minidev.json.JSONObject;

import java.util.ArrayList;

public interface ArticleService {
    ArticleDTO createArticle(RegisterArticleDTO registerArticleDTO, AuthMemberDTO authMemberDTO);

    ArticleDTO getArticleBySlug(String slug, AuthMemberDTO authMemberDTO);

    ArticleDTO updateArticle(String slug, UpdateArticleDTO updateArticleDTO, AuthMemberDTO authMemberDTO);

    void deleteArticle(String slug, AuthMemberDTO authMemberDTO);

    ArticleDTO saveArticleFavorite(String slug, AuthMemberDTO authMemberDTO);

    ArticleDTO deleteArticleFavorite(String slug, AuthMemberDTO authMemberDTO);

    JSONObject getArticles(ArticleSearch articleSearch, AuthMemberDTO authMemberDTO);

    default Article registerArticleToEntity(RegisterArticleDTO registerArticleDTO) {
        Article article = Article.builder()
                .title(registerArticleDTO.getTitle())
                .description(registerArticleDTO.getDescription())
                .body(registerArticleDTO.getBody())
                .articleTags(new ArrayList<>())
                .build();
        article.setSlugify();

        return article;
    }
}
