package com.risingtest.wanted.src.hashtag;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicHashtag {
    private long id;

    private String name;

    public static BasicHashtag from(Hashtag hashtag){
        return new BasicHashtag(hashtag.getId(), hashtag.getName());
    }
}
