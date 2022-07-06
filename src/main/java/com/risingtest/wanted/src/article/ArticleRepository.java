package com.risingtest.wanted.src.article;


import com.risingtest.wanted.src.article.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByTagsContains(String tag);
}
