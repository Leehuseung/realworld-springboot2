package com.kr.realworldspringboot2.article.favorite;

import com.kr.realworldspringboot2.article.Article;
import com.kr.realworldspringboot2.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleFavoriteRepository extends JpaRepository<ArticleFavorite,Long> {

    Long countByArticleAndMember(Article article, Member member);

    void deleteArticleFavoritesByArticle(Article article);

    @Transactional
    @Modifying
    void deleteArticleFavoriteByArticleAndMember(Article article, Member member);

}
