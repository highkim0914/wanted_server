package com.risingtest.wanted.src.articlecomment;

import com.risingtest.wanted.src.article.Article;
import com.risingtest.wanted.src.user.model.User;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostArticleCommentReq {
    private long articleId;
    private String detail;

    public ArticleComment toEntity(Article article, User user){
        return ArticleComment.builder()
                .article(article)
                .user(user)
                .detail(detail)
                .build();
    }
}
