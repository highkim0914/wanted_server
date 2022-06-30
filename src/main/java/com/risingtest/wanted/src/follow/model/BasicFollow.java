package com.risingtest.wanted.src.follow.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicFollow {
    private long companyId;
    private int status;

    public static BasicFollow from(Follow follow){
        return BasicFollow.builder()
                .companyId(follow.getCompany().getId())
                .status(follow.getStatus())
                .build();
    }
}
