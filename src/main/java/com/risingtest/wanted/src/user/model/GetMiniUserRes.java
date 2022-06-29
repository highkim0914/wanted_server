package com.risingtest.wanted.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetMiniUserRes {
    private String userName;
    private String email;
    private String photoUrl;

    public static GetMiniUserRes from(User user) {
        return GetMiniUserRes.builder()
                .userName(user.getUserName())
                .email(user.getEmail())
                .photoUrl(user.getPhotoUrl())
                .build();
    }
}
