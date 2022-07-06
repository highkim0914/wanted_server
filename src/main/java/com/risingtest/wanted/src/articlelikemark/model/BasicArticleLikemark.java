package com.risingtest.wanted.src.articlelikemark.model;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicArticleLikemark {
    private long articleId;
    private int status;

    public static BasicArticleLikemark from(ArticleLikemark articleLikemark){
        return BasicArticleLikemark.builder()
                .articleId(articleLikemark.getArticle().getId())
                .status(articleLikemark.getStatus())
                .build();
    }
}
