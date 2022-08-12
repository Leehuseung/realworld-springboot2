package com.kr.realworldspringboot2.article;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    Article findBySlug(String slug);

    int countArticleBySlug(String slug);
}
