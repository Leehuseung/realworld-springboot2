package com.kr.realworldspringboot2.article;

import com.kr.realworldspringboot2.article.tag.Tag;
import com.kr.realworldspringboot2.member.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "articletag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
}
