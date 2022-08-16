package com.kr.realworldspringboot2.article.comment;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.kr.realworldspringboot2.member.AuthorDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@JsonTypeName("comment")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class CommentDTO {
    Long id;
    String body;
    ZonedDateTime createdAt;
    ZonedDateTime updatedAt;
    AuthorDTO author;
}
