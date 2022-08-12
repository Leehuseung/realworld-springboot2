package com.kr.realworldspringboot2.article.favorite;

import com.kr.realworldspringboot2.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleFavoriteRepository extends JpaRepository<ArticleFavorite,Long> {
}
