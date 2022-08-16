package com.kr.realworldspringboot2.article.comment;

import com.kr.realworldspringboot2.article.Article;
import com.kr.realworldspringboot2.article.ArticleRepository;
import com.kr.realworldspringboot2.member.Member;
import com.kr.realworldspringboot2.member.MemberRepository;
import com.kr.realworldspringboot2.security.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<CommentDTO> getComments(String slug, AuthMemberDTO authMemberDTO) {
        Article article = articleRepository.findBySlug(slug);
        Member loginMember = authMemberDTO == null ? null : memberRepository.findById(authMemberDTO.getId()).orElse(null);

        List<Comment> comments = commentRepository.findCommentsByArticleOrderByCreatedAtDesc(article);
        List<CommentDTO> dtoList = new ArrayList<>();
        for (Comment comment : comments) {
            dtoList.add(comment.toCommentDTO(loginMember));
        }

        return dtoList;
    }

    @Override
    public CommentDTO addComment(String slug, RegisterCommentDTO registerCommentDTO, AuthMemberDTO authMemberDTO) {
        Article article = articleRepository.findBySlug(slug);
        Member loginMember = memberRepository.findById(authMemberDTO.getId()).get();
        Comment comment = registerCommentToEntity(registerCommentDTO,article,loginMember);
        commentRepository.save(comment);
        return comment.toCommentDTO(loginMember);
    }

    @Override
    public void deleteComment(Long id, AuthMemberDTO authMemberDTO) {
        Member loginMember = memberRepository.findById(authMemberDTO.getId()).get();
        commentRepository.deleteByIdAndMemberEquals(id,loginMember);
    }

}
