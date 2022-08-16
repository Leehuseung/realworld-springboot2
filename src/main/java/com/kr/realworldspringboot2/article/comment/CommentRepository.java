package com.kr.realworldspringboot2.article.comment;

import com.kr.realworldspringboot2.article.Article;
import com.kr.realworldspringboot2.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findCommentsByArticleOrderByCreatedAtDesc(Article article);

    @Modifying
    @Transactional
    @Query(value = "delete from Comment c where c.id = :id and c.member = :member")
    void deleteByIdAndMemberEquals(Long id, Member member);
}
