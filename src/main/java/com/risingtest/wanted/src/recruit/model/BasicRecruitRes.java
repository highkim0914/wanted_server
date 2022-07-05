package com.risingtest.wanted.src.recruit.model;

import com.risingtest.wanted.src.company.model.Company;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BasicRecruitRes {
    private Long id;

    private String title;

    private long companyId;

    private String companyName;

    private String address;

    private String detail;

    //private List<Long> hashtags;

    private double responseRate;

    private String jobGroup;

    private String position;

    private int career;

    //private List<Long> techstacks;

    private String location;

    private LocalDate deadline;

    private String[] photos;

    private String profilePhoto;

    private long views;

    public static BasicRecruitRes from(Recruit recruit) {
        Company company = recruit.getCompany();
        long companyId= company.getId();
        String companyName = company.getName();
        String companyPhotos = company.getPhotoUrl()==null? "" :company.getPhotoUrl();
        String[] photoUrlList = companyPhotos.split(",");
        return BasicRecruitRes.builder()
                .id(recruit.getId())
                .title(recruit.getTitle())
                .companyId(companyId)
                .companyName(companyName)
                .address(company.getAddress())
                .detail(recruit.getDetail())
                .responseRate(recruit.getResponseRate())
                .jobGroup(recruit.getJobGroup())
                .position(recruit.getPosition())
                .career(recruit.getCareer())
                .location(recruit.getLocation())
                .deadline(recruit.getDeadline())
                .photos(photoUrlList)
                .profilePhoto(company.getProfilePhotoUrl())
                .views(recruit.getViews())
                .build();

    }
}
