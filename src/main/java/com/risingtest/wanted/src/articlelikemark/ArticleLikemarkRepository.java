package com.risingtest.wanted.src.articlelikemark;

import com.risingtest.wanted.src.articlelikemark.model.ArticleLikemark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleLikemarkRepository extends JpaRepository<ArticleLikemark, Long> {
    long countByArticleIdAndStatus(long articleId, int status);

    Optional<ArticleLikemark> findByArticleIdAndUserId(long articleId, long userId);
}
