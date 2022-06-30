package com.risingtest.wanted.src.jobapplication;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostJobApplicationReq {
    private String name;
    private String email;
    private String phoneNumber;
    private String recommender;
    private Long resumeId;
}
