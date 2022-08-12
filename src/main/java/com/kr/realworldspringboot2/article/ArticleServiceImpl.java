package com.kr.realworldspringboot2.article;

import com.kr.realworldspringboot2.article.tag.Tag;
import com.kr.realworldspringboot2.article.tag.TagRepository;
import com.kr.realworldspringboot2.exception.DuplicateRegisterException;
import com.kr.realworldspringboot2.member.Member;
import com.kr.realworldspringboot2.member.MemberRepository;
import com.kr.realworldspringboot2.security.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final ArticleTagRepository articleTagRepository;
    private final MemberRepository memberRepository;

    @Override
    public ArticleDTO createArticle(RegisterArticleDTO registerArticleDTO, AuthMemberDTO authMemberDTO) {
        Article article = registerArticleToEntity(registerArticleDTO);
        Member loginMember = memberRepository.findById(authMemberDTO.getId()).get();

        if(articleRepository.countArticleBySlug(article.getSlug()) > 0){
            throw new DuplicateRegisterException("article");
        }

        article.setMember(loginMember);
        articleRepository.save(article);

        List<String> tagList = registerArticleDTO.getTagList();
        if(tagList != null){
            insertTag(article, tagList);
        }

        Article newArticle = articleRepository.findById(article.getId()).get();

        ArticleDTO articleDTO = newArticle.toArticleDTO(loginMember);

        return articleDTO;
    }

    private void insertTag(Article article, List<String> tagList) {
        for (String tagName : tagList) {
            Tag tag;
            if (tagRepository.countByName(tagName) == 0) {
                tag = Tag.builder()
                        .name(tagName)
                        .build();
                tagRepository.save(tag);
            } else {
                tag = tagRepository.findByName(tagName);
            }
            ArticleTag articleTag = ArticleTag.builder()
                    .article(article)
                    .tag(tag)
                    .build();

            article.getArticleTags().add(articleTag);
            articleTagRepository.save(articleTag);
        }
    }

}
