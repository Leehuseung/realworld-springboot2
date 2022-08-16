package com.kr.realworldspringboot2.article.comment;

import com.kr.realworldspringboot2.article.Article;
import com.kr.realworldspringboot2.member.Member;
import com.kr.realworldspringboot2.security.AuthMemberDTO;

import java.util.List;

public interface CommentService {

    List<CommentDTO> getComments(String slug, AuthMemberDTO authMemberDTO);

    CommentDTO addComment(String slug, RegisterCommentDTO registerCommentDTO, AuthMemberDTO authMemberDTO);

    default Comment registerCommentToEntity(RegisterCommentDTO registerCommentDTO, Article article, Member member) {
        Comment comment = Comment.builder()
                .body(registerCommentDTO.getBody())
                .article(article)
                .member(member)
                .build();

        return comment;
    }

}
