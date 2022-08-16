package com.kr.realworldspringboot2.article.comment;

import com.kr.realworldspringboot2.security.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/articles/{slug}/comments")
    public JSONObject getComments(@PathVariable String slug,
                                  @AuthenticationPrincipal AuthMemberDTO authMemberDTO){
        JSONObject result = new JSONObject();
        result.put("comments",commentService.getComments(slug,authMemberDTO));
        return result;
    }

    @PostMapping("/api/articles/{slug}/comments")
    public CommentDTO addComment(@PathVariable String slug,
                                 @RequestBody @Valid RegisterCommentDTO registerCommentDTO,
                                 @AuthenticationPrincipal AuthMemberDTO authMemberDTO){
        return commentService.addComment(slug,registerCommentDTO,authMemberDTO);
    }


}
