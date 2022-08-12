package com.kr.realworldspringboot2.article.favorite;

import com.kr.realworldspringboot2.article.Article;
import com.kr.realworldspringboot2.member.Member;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    Article article;

    @ManyToOne
    @JoinColumn(name = "member_id")
    Member member;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleFavorite that = (ArticleFavorite) o;
        return Objects.equals(article, that.article) && Objects.equals(member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(article, member);
    }
}
