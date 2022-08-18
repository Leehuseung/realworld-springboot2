package com.kr.realworldspringboot2.article;

import com.kr.realworldspringboot2.member.Member;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kr.realworldspringboot2.article.QArticle.article;
import static com.kr.realworldspringboot2.article.QArticleTag.articleTag;
import static com.kr.realworldspringboot2.article.favorite.QArticleFavorite.articleFavorite;
import static com.kr.realworldspringboot2.article.tag.QTag.tag;
import static com.kr.realworldspringboot2.member.QMember.member;
import static com.kr.realworldspringboot2.profile.QFollow.follow;

@AllArgsConstructor
@Repository
public class ArticleQueryRepository {

    private JPAQueryFactory jpaQueryFactory;

    public List<Article> getArticle(ArticleSearch articleSearch){

        List<Article> articles = jpaQueryFactory
                .select(article)
                .from(article)
                .join(article.member, member)
                .where(tagNameEq(articleSearch.getTag()),
                        usernameEq(articleSearch.getAuthor()))
                .orderBy(article.createdAt.desc())
                .offset(articleSearch.getOffset())
                .limit(articleSearch.getLimit())
                .fetch();

        return articles;
    }

    public int getArticleCount(ArticleSearch articleSearch) {
        int cnt = jpaQueryFactory
                .select(article)
                .from(article)
                .join(article.member, member)
                .where(usernameEq(articleSearch.getAuthor()))
                .fetch().size();
        return cnt;
    }

    public int getArticleByTagCount(ArticleSearch articleSearch) {
        int cnt = jpaQueryFactory
                .select(articleTag.article)
                .from(articleTag)
                .join(articleTag.tag, tag)
                .where(tagNameEq(articleSearch.getTag()))
                .fetch().size();
        return cnt;
    }

    public List<Article> getArticleByTag(ArticleSearch articleSearch){
        List<Article> articles = jpaQueryFactory
                .select(articleTag.article)
                .from(articleTag)
                .join(articleTag.tag,tag)
                .where(tagNameEq(articleSearch.getTag()))
                .orderBy(articleTag.article.createdAt.desc())
                .offset(articleSearch.getOffset())
                .limit(articleSearch.getLimit())
                .fetch();

        return articles;
    }

    private Predicate tagNameEq(String tag) {
        return tag == null ? null : articleTag.tag.name.eq(tag);
    }

    private Predicate usernameEq(String usernmae) {
        return usernmae == null ? null : article.member.username.eq(usernmae);
    }

    public List<Article> getArticleByFavorite(ArticleSearch articleSearch) {
        List<Article> articles = jpaQueryFactory
                .select(articleFavorite.article)
                .from(articleFavorite)
                .join(articleFavorite.article,article)
                .join(articleFavorite.member,member)
                .where(articleFavorite.member.username.eq(articleSearch.getFavorited()))
                .orderBy(articleFavorite.article.createdAt.desc())
                .offset(articleSearch.getOffset())
                .limit(articleSearch.getLimit())
                .fetch();

        return articles;
    }

    public int getArticleByFavoriteCount(ArticleSearch articleSearch) {
        int cnt = jpaQueryFactory
                .select(articleFavorite.article)
                .from(articleFavorite)
                .join(articleFavorite.article,article)
                .join(articleFavorite.member,member)
                .where(articleFavorite.member.username.eq(articleSearch.getFavorited()))
                .fetch().size();
        return cnt;
    }

    public int getFeedsCount(Member searchMember) {
        int cnt = jpaQueryFactory
                .select(article)
                .from(article)
                .join(article.member,member)
                .where(member.in(
                        JPAExpressions
                                .select(follow.followMember)
                                .from(follow)
                                .where(follow.member.eq(searchMember))
                ))
                .fetch().size();

        return cnt;
    }

    public List<Article> getFeeds(ArticleSearch articleSearch,Member searchMember) {
        List<Article> articles = jpaQueryFactory
                .select(article)
                .from(article)
                .join(article.member,member)
                .where(member.in(
                        JPAExpressions
                                .select(follow.followMember)
                                .from(follow)
                                .where(follow.member.eq(searchMember))
                ))
                .orderBy(article.createdAt.desc())
                .offset(articleSearch.getOffset())
                .limit(articleSearch.getLimit())
                .fetch();

        return articles;
    }


}
