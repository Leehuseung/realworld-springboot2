package com.kr.realworldspringboot2.article;

import com.kr.realworldspringboot2.article.tag.TagRepository;
import com.kr.realworldspringboot2.member.MemberRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {
    @Mock
    ArticleRepository articleRepository;

    @Mock
    TagRepository tagRepository;

    @Mock
    ArticleTagRepository articleTagRepository;

    @InjectMocks
    MemberRepository memberRepository;


}