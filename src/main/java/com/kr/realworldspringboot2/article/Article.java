package com.kr.realworldspringboot2.article;

import com.kr.realworldspringboot2.article.favorite.ArticleFavorite;
import com.kr.realworldspringboot2.member.BaseEntity;
import com.kr.realworldspringboot2.member.Member;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARTICLE_ID")
    private Long id;
    @NotEmpty
    private String slug;
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    @NotEmpty
    private String body;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "article", cascade = {CascadeType.REMOVE})
    @Builder.Default
    private List<ArticleTag> articleTags = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = {CascadeType.REMOVE})
    @Builder.Default
    private Set<ArticleFavorite> articleFavorites = new HashSet<>();

    public List<String> getTagList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < articleTags.size(); i++) {
            list.add(articleTags.get(i).getTag().getName());
        }
        return list;
    }

    private boolean isFavorite(Member loginMember){
        ArticleFavorite articleFavorite = ArticleFavorite.builder()
                .article(this)
                .member(loginMember)
                .build();

        return articleFavorites.contains(articleFavorite);
    }

    public ArticleDTO toArticleDTO(Member loginMember) {
        ArticleDTO articleDTO = ArticleDTO.builder()
                .slug(this.slug)
                .title(this.title)
                .description(this.description)
                .body(this.body)
                .createdAt(this.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")))
                .updatedAt(this.getUpdatedAt().atZone(ZoneId.of("Asia/Seoul")))
                .tagList(this.getTagList())
                .favorited(isFavorite(loginMember))
                .favoritesCount((long) this.articleFavorites.size())
                .author(member.toAuthorDTO(loginMember))
                .build();

        return articleDTO;
    }


    public void setSlugify(){
        this.slug = title.replaceAll(" ","-").toLowerCase();
    }



}
