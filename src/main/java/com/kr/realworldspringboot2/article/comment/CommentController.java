package com.kr.realworldspringboot2.article.comment;

import com.kr.realworldspringboot2.security.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/articles/{slug}/comments")
    public CommentDTO addComment(@PathVariable String slug,
                                 @RequestBody @Valid RegisterCommentDTO registerCommentDTO,
                                 @AuthenticationPrincipal AuthMemberDTO authMemberDTO){
        return commentService.addComment(slug,registerCommentDTO,authMemberDTO);
    }
}
