package com.kr.realworldspringboot2.article;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("article")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class RegisterArticleDTO {
    @NotEmpty(message = "title can't be empty")
    private String title;
    @NotEmpty(message = "description can't be empty")
    private String description;
    @NotEmpty(message = "body can't be empty")
    private String body;

    private List<String> tagList;
}
