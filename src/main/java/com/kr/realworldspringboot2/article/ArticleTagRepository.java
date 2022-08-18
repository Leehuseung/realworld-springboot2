package com.kr.realworldspringboot2.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {

    @Transactional
    @Modifying
    void deleteArticleTagsByArticle(Article article);

}
