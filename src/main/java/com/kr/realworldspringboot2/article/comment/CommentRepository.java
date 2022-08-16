package com.kr.realworldspringboot2.article.comment;

import com.kr.realworldspringboot2.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findCommentsByArticleOrderByCreatedAtDesc(Article article);

}
