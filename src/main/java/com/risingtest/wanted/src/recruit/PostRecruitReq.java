package com.risingtest.wanted.src.recruit;

import com.risingtest.wanted.src.company.Company;
import com.risingtest.wanted.src.recruit.model.BasicRecruitRes;
import com.risingtest.wanted.src.recruit.model.Recruit;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PostRecruitReq  {
    private Long companyId;

    private String address;

    private String detail;

    private String job_group;

    private String position;

    private int career;

    private String title;

    private String location;

    private LocalDate deadline;

    private long views;

    public Recruit toEntity(Company company){
        return Recruit.builder()
                .title(this.getTitle())
                .company(company)
                .detail(this.getDetail())
                .location(this.getLocation())
                .deadline(this.getDeadline())
                .job_group(this.getJob_group())
                .career(this.getCareer())
                .position(this.getPosition())
                .response_rate(0.8)
                .views(0L)
                .bookmarks(new ArrayList<>())
                .jobApplications(new ArrayList<>())
                .likeMarks(new ArrayList<>())
                .build();
    }
}
