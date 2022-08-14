package com.kr.realworldspringboot2.article;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("article")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class UpdateArticleDTO {
    private String title;
    private String description;
    private String body;
    private List<String> tagList;

    void applyTo(Article article) {
        if(this.getTitle() != null) {
            article.setTitle(this.getTitle());
            article.setSlugify();
        }
        if(this.getDescription() != null) article.setDescription(this.getDescription());
        if(this.getBody() != null) article.setBody(this.getBody());
    }
}
