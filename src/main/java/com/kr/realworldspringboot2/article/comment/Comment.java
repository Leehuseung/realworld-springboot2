package com.kr.realworldspringboot2.article.comment;

import com.kr.realworldspringboot2.article.Article;
import com.kr.realworldspringboot2.member.BaseEntity;
import com.kr.realworldspringboot2.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.ZoneId;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    private String body;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    public CommentDTO toCommentDTO(Member loginMember) {
        return CommentDTO.builder()
                .id(this.id)
                .body(this.body)
                .createdAt(this.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")))
                .updatedAt(this.getUpdatedAt().atZone(ZoneId.of("Asia/Seoul")))
                .author(member.toAuthorDTO(loginMember))
                .build();
    }
}
