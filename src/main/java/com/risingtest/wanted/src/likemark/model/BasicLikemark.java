package com.risingtest.wanted.src.likemark.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicLikemark {
    private long recruitId;
    private int status;

    public static BasicLikemark from(Likemark likeMark){
        return BasicLikemark.builder()
                .recruitId(likeMark.getRecruit().getId())
                .status(likeMark.getStatus())
                .build();
    }
}
