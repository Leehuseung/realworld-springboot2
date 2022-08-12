package com.kr.realworldspringboot2.article;

import com.kr.realworldspringboot2.security.AuthMemberDTO;

import java.util.ArrayList;

public interface ArticleService {
    ArticleDTO createArticle(RegisterArticleDTO registerArticleDTO, AuthMemberDTO authMemberDTO);

    ArticleDTO getArticleBySlug(String slug, AuthMemberDTO authMemberDTO);

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
