package com.kr.realworldspringboot2.article;

import com.kr.realworldspringboot2.article.favorite.ArticleFavorite;
import com.kr.realworldspringboot2.article.favorite.ArticleFavoriteRepository;
import com.kr.realworldspringboot2.article.tag.Tag;
import com.kr.realworldspringboot2.article.tag.TagRepository;
import com.kr.realworldspringboot2.exception.DuplicateRegisterException;
import com.kr.realworldspringboot2.exception.NotUserOwnException;
import com.kr.realworldspringboot2.member.Member;
import com.kr.realworldspringboot2.member.MemberRepository;
import com.kr.realworldspringboot2.security.AuthMemberDTO;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final ArticleTagRepository articleTagRepository;
    private final ArticleQueryRepository articleQueryRepository;
    private final ArticleFavoriteRepository articleFavoriteRepository;
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

        return article.toArticleDTO(loginMember);
    }

    @Override
    public ArticleDTO getArticleBySlug(String slug, AuthMemberDTO authMemberDTO) {
        Member loginMember = authMemberDTO == null ? null : memberRepository.findById(authMemberDTO.getId()).orElse(null);
        Article article = articleRepository.findBySlug(slug);
        return article.toArticleDTO(loginMember);
    }

    @Override
    public ArticleDTO updateArticle(String slug, UpdateArticleDTO updateArticleDTO, AuthMemberDTO authMemberDTO) {
        Article article = articleRepository.findBySlug(slug);
        Member loginMember = memberRepository.findById(authMemberDTO.getId()).get();
        if(!article.getMember().getId().equals(authMemberDTO.getId())){
            throw new NotUserOwnException("article");
        }

        List<String> tagList = updateArticleDTO.getTagList();
        articleTagRepository.deleteArticleTagsByArticle(article);
        article.setArticleTags(new ArrayList<>());
        if(tagList != null){
            insertTag(article, tagList);
        }

        updateArticleDTO.applyTo(article);
        articleRepository.save(article);
        return article.toArticleDTO(loginMember);
    }

    @Override
    public void deleteArticle(String slug, AuthMemberDTO authMemberDTO) {
        Article article = articleRepository.findBySlug(slug);
        if(!article.getMember().getId().equals(authMemberDTO.getId())){
           throw new NotUserOwnException("article");
        }
        articleFavoriteRepository.deleteArticleFavoritesByArticle(article);
        articleTagRepository.deleteArticleTagsByArticle(article);
        articleRepository.delete(article);

    }

    @Override
    public ArticleDTO saveArticleFavorite(String slug, AuthMemberDTO authMemberDTO) {
        Member loginMember = memberRepository.findById(authMemberDTO.getId()).get();
        Article article = articleRepository.findBySlug(slug);

        ArticleFavorite articleFavorite = ArticleFavorite.builder()
                .article(article)
                .member(loginMember)
                .build();

        if(articleFavoriteRepository.countByArticleAndMember(article,loginMember) == 0){
            articleFavoriteRepository.save(articleFavorite);
            article.getArticleFavorites().add(articleFavorite);
        }

        return article.toArticleDTO(loginMember);
    }

    @Override
    public ArticleDTO deleteArticleFavorite(String slug, AuthMemberDTO authMemberDTO) {
        Member loginMember = memberRepository.findById(authMemberDTO.getId()).get();
        Article article = articleRepository.findBySlug(slug);
        ArticleFavorite articleFavorite = ArticleFavorite.builder()
                .article(article)
                .member(loginMember)
                .build();

        article.getArticleFavorites().remove(articleFavorite);
        articleFavoriteRepository.deleteArticleFavoriteByArticleAndMember(article,loginMember);
        return article.toArticleDTO(loginMember);
    }

    @Override
    public JSONObject getArticles(ArticleSearch articleSearch, AuthMemberDTO authMemberDTO) {
        Member loginMember = authMemberDTO == null ? null : memberRepository.findById(authMemberDTO.getId()).orElse(null);
        int cnt = 0;
        List<Article> list = null;
        if(articleSearch.getTag() != null){
            list = articleQueryRepository.getArticleByTag(articleSearch);
            cnt = articleQueryRepository.getArticleByTagCount(articleSearch);
        } else if(articleSearch.getFavorited() != null){
            list = articleQueryRepository.getArticleByFavorite(articleSearch);
            cnt = articleQueryRepository.getArticleByFavoriteCount(articleSearch);
        } else {
            list = articleQueryRepository.getArticle(articleSearch);
            cnt = articleQueryRepository.getArticleCount(articleSearch);
        }

        List<ArticleDTO> dtoList = new ArrayList<>();
        for (Article article : list) {
            dtoList.add(article.toArticleDTO(loginMember));
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("articlesCount",cnt);
        jsonObject.put("articles",dtoList);
        return jsonObject;
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
