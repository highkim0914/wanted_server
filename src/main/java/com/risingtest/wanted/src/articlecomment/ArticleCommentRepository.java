package com.risingtest.wanted.src.articlecomment;

import com.risingtest.wanted.src.articlecomment.model.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
