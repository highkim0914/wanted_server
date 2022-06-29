package com.risingtest.wanted.src.bookmark;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicBookmark {
    private long recruitId;
    private int status;

    public static BasicBookmark from(Bookmark bookmark){
        return BasicBookmark.builder()
                .recruitId(bookmark.getRecruit().getId())
                .status(bookmark.getStatus())
                .build();
    }
}
