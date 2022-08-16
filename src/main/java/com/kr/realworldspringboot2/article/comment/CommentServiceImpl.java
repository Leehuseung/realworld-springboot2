package com.kr.realworldspringboot2.article.comment;

import com.kr.realworldspringboot2.article.Article;
import com.kr.realworldspringboot2.article.ArticleRepository;
import com.kr.realworldspringboot2.member.Member;
import com.kr.realworldspringboot2.member.MemberRepository;
import com.kr.realworldspringboot2.security.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Override
    public CommentDTO addComment(String slug, RegisterCommentDTO registerCommentDTO, AuthMemberDTO authMemberDTO) {
        Article article = articleRepository.findBySlug(slug);
        Member loginMember = memberRepository.findById(authMemberDTO.getId()).get();
        Comment comment = registerCommentToEntity(registerCommentDTO,article,loginMember);
        commentRepository.save(comment);
        return comment.toCommentDTO(loginMember);
    }

}
