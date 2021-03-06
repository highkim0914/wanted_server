package com.risingtest.wanted.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetUserRes {
    private long id;
    private String userName;
    private String email;
    private String phoneNumber;
    private String photoUrl;

    public static GetUserRes from(User user) {
        return GetUserRes.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .photoUrl(user.getPhotoUrl())
                .build();
    }
}
