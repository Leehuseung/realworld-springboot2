package com.kr.realworldspringboot2.article;

import com.kr.realworldspringboot2.security.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/api/articles")
    public ArticleDTO createArticle(@RequestBody @Valid RegisterArticleDTO registerArticleDTO,
                                    @AuthenticationPrincipal AuthMemberDTO authMemberDTO) {

        return articleService.createArticle(registerArticleDTO,authMemberDTO);
    }

}
