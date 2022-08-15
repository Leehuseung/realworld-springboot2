package com.kr.realworldspringboot2.article;

import com.kr.realworldspringboot2.security.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/api/articles/{slug}")
    public ArticleDTO getArticle(@PathVariable String slug,
                                 @AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        return articleService.getArticleBySlug(slug, authMemberDTO);
    }

    @DeleteMapping("/api/articles/{slug}")
    public void deleteArticle(@PathVariable String slug,
                              @AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        articleService.deleteArticle(slug,authMemberDTO);
    }

    @PutMapping("/api/articles/{slug}")
    public ArticleDTO updateArticle(@PathVariable String slug,
                                    @RequestBody UpdateArticleDTO updateArticleDTO,
                                    @AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        return articleService.updateArticle(slug,updateArticleDTO,authMemberDTO);
    }

    @PostMapping("/api/articles/{slug}/favorite")
    public ArticleDTO favoriteArticle(@PathVariable String slug,
                                      @AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        return articleService.saveArticleFavorite(slug, authMemberDTO);
    }

    @DeleteMapping("/api/articles/{slug}/favorite")
    public ArticleDTO unFavoriteArticle(@PathVariable String slug,
                                        @AuthenticationPrincipal AuthMemberDTO authMemberDTO) {
        return articleService.deleteArticleFavorite(slug, authMemberDTO);
    }

}
