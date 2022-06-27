package com.risingtest.wanted.src.recruit.dto;

import com.risingtest.wanted.src.company.Company;
import com.risingtest.wanted.src.hashtag.RecruitHashtag;
import com.risingtest.wanted.src.recruit.Recruit;
import com.risingtest.wanted.src.techstack.RecruitTechstack;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicRecruitRes {
    private Long id;

    private String title;

    private long companyId;

    private String companyName;

    private List<Long> recruitHashtags;

    private String detail;

    private double response_rate;

    private String job_group;

    private String position;

    private int career;

    private List<Long> recruitTechstacks;

    private String location;

    private LocalDate deadline;

    private String photos;

    private long views;

    public static BasicRecruitRes from(Recruit recruit) {
        Company company = recruit.getCompany();
        long companyId= company.getId();
        String companyName = company.getName();
        return BasicRecruitRes.builder()
                .id(recruit.getId())
                .title(recruit.getTitle())
                .companyId(companyId)
                .companyName(companyName)
                .recruitHashtags(recruit.getRecruitHashtags().stream()
                        .map(o -> o.getHashtag().getId())
                        .collect(Collectors.toList()))
                .detail(recruit.getDetail())
                .response_rate(recruit.getResponse_rate())
                .job_group(recruit.getJob_group())
                .position(recruit.getPosition())
                .career(recruit.getCareer())
                .recruitTechstacks(recruit.getRecruitTechstacks().stream()
                        .map(o->o.getTechstack().getId())
                        .collect(Collectors.toList()))
                .location(recruit.getLocation())
                .deadline(recruit.getDeadline())
                .photos(recruit.getPhotos()==null?"": recruit.getPhotos())
                .views(recruit.getViews())
                .build();

    }
}
