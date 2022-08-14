package com.kr.realworldspringboot2.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    Article findBySlug(String slug);

    int countArticleBySlug(String slug);

}
