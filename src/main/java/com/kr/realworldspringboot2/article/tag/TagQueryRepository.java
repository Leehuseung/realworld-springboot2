package com.kr.realworldspringboot2.article.tag;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kr.realworldspringboot2.article.QArticleTag.articleTag;
import static com.kr.realworldspringboot2.article.tag.QTag.tag;

@AllArgsConstructor
@Repository
public class TagQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<TagDTO> getTags(){

        List<TagDTO> tags = jpaQueryFactory
                .select(Projections.constructor(TagDTO.class,tag.id,tag.name,tag.count()))
                .from(tag, articleTag)
                .where(tag.id.eq(articleTag.tag.id))
                .groupBy(tag.name)
                .orderBy(tag.count().desc(),
                        tag.name.asc())
                .offset(0)
                .limit(10)
                .fetch();

        return tags;
    }

}
