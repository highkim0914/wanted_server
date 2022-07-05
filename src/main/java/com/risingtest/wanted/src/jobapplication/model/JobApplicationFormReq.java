package com.risingtest.wanted.src.jobapplication.model;

import com.risingtest.wanted.src.resume.model.BasicResume;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplicationFormReq {
    private String name;
    private String email;
    private String phoneNumber;
    private String recommender;
    private List<BasicResume> basicResumes;
}
