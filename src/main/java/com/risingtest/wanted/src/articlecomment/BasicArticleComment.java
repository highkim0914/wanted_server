package com.risingtest.wanted.src.articlecomment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicArticleComment {
    private long id;
    private String nickname;
    private String detail;
    private String userPhotoUrl;
    private LocalDateTime createdAt;

    public static BasicArticleComment from(ArticleComment articleComment){
        return BasicArticleComment.builder()
                .id(articleComment.getId())
                .nickname(articleComment.getUser().getCommunityNickname())
                .detail(articleComment.getDetail())
                .userPhotoUrl(articleComment.getUser().getPhotoUrl())
                .createdAt(articleComment.getCreatedAt())
                .build();
    }
}
