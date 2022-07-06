package com.risingtest.wanted.src.user.model;

import com.risingtest.wanted.src.jobapplication.model.BasicJobApplication;
import com.risingtest.wanted.src.recruit.model.BasicRecruitRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetMyPageRes {
    private String email;
    private String phoneNumber;
    private String name;
    private List<BasicJobApplication> jobApplications;
    private List<BasicRecruitRes> bookmarks;
    private List<BasicRecruitRes> likemarks;
}
