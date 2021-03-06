package com.risingtest.wanted.src.recruit.model;

import com.risingtest.wanted.src.company.model.Company;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PostRecruitReq  {
    private Long companyId;

    private String address;

    private String detail;

    private String jobGroup;

    private String position;

    private int career;

    private String title;

    private LocalDate deadline;

    public Recruit toEntity(Company company){
        return Recruit.builder()
                .title(this.getTitle())
                .company(company)
                .detail(this.getDetail())
                .location(company.getLocation())
                .deadline(this.getDeadline())
                .jobGroup(this.getJobGroup())
                .career(this.getCareer())
                .position(this.getPosition())
                .responseRate(company.getResponseRate())
                .views(0L)
//                .bookmarks(new ArrayList<>())
//                .jobApplications(new ArrayList<>())
//                .likeMarks(new ArrayList<>())
                .build();
    }
}
