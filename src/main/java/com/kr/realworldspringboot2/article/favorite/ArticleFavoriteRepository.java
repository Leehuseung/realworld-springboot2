package com.kr.realworldspringboot2.article.favorite;

import com.kr.realworldspringboot2.article.Article;
import com.kr.realworldspringboot2.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleFavoriteRepository extends JpaRepository<ArticleFavorite,Long> {

    Long countByArticleAndMember(Article article, Member member);

    void deleteArticleFavoritesByArticle(Article article);

    void deleteArticleFavoriteByArticleAndMember(Article article, Member member);

}
