package com.risingtest.wanted.src.article;

import java.util.ArrayList;
import java.util.List;

import com.risingtest.wanted.src.user.model.User;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostArticleReq {
    private String title;
    private String detail;
    private List<String> tags;

    public Article toEntity(User user){
        return Article.builder()
                .title(title)
                .detail(detail)
                .tags(String.join(",", tags))
                .user(user)
                .articleCommentList(new ArrayList<>())
                .build();
    }
}
