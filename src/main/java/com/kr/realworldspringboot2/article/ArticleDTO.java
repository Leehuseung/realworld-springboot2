package com.kr.realworldspringboot2.article;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.kr.realworldspringboot2.member.AuthorDTO;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("article")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class ArticleDTO {
    String slug;
    String title;
    String description;
    String body;
    ZonedDateTime createdAt;
    ZonedDateTime updatedAt;
    List<String> tagList;
    boolean favorited;
    Long favoritesCount;
    AuthorDTO author;
}
