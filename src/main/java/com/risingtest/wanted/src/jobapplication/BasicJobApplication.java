package com.risingtest.wanted.src.jobapplication;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.risingtest.wanted.src.resume.model.ResumeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicJobApplication {
    private long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String recommender;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    public static BasicJobApplication from(JobApplication jobApplication){
        return BasicJobApplication.builder()
                .id(jobApplication.getId())
                .name(jobApplication.getName())
                .email(jobApplication.getEmail())
                .phoneNumber(jobApplication.getPhoneNumber())
                .recommender(jobApplication.getRecommender())
                .applicationStatus(jobApplication.getApplicationStatus())
                .build();
    }
}
