package com.risingtest.wanted.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUserReq {
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;

    public User toEntity(){
        return User.builder()
                .userName(userName)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .career(0)
                .jobGroup("")
                .lookingForJob(LookingForJobStatus.LOOKINGFORJOB)
                .positions("")
                .techStacks("")
                .communityNickname("")
                .build();
    }
}
