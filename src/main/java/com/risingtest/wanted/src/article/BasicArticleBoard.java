package com.risingtest.wanted.src.article;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicArticleBoard {
    private List<BasicArticle> basicArticles;
}
