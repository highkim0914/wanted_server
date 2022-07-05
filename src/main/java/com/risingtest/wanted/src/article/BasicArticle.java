package com.risingtest.wanted.src.article;

import com.risingtest.wanted.src.articlecomment.BasicArticleComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicArticle {
    private long id;
    private String detail;
    private String nickname;
    private String userPhotoUrl;
    private LocalDateTime createdAt;

    private Long likemarkCount;
    private Long commentCount;
    private List<BasicArticleComment> comments;
    private ArrayList<String> tags;

    public static BasicArticle from(Article article, long likemarkCount, List<BasicArticleComment> comments){
        ArrayList<String> articleTags = new ArrayList<>(Arrays.asList(article.getTags().split(",")));
        return BasicArticle.builder()
                .id(article.getId())
                .detail(article.getDetail())
                .nickname(article.getUser().getCommunityNickname())
                .userPhotoUrl(article.getUser().getPhotoUrl())
                .createdAt(article.getCreatedAt())
                .likemarkCount(likemarkCount)
                .commentCount((long) comments.size())
                .comments(comments)
                .tags(articleTags)
                .build();
    }
}
