package com.risingtest.wanted.src.recruit.model;

import com.risingtest.wanted.src.company.model.Company;
import com.risingtest.wanted.src.hashtag.CompanyHashtag;
import com.risingtest.wanted.src.hashtag.model.Hashtag;
import com.risingtest.wanted.src.techstack.CompanyTechstack;
import com.risingtest.wanted.src.techstack.model.Techstack;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GetRecruitRes extends BasicRecruitRes{
    private List<Long> hashtags;

    private List<Long> techstacks;

    public static GetRecruitRes from(Recruit recruit, Company company){
        String companyPhotos = company.getPhotoUrl()==null? "" :company.getPhotoUrl();
        String[] photoUrlList = companyPhotos.split(",");
        return GetRecruitRes.builder()
                .id(recruit.getId())
                .title(recruit.getTitle())
                .companyId(company.getId())
                .companyName(company.getName())
                .address(company.getAddress())
                .detail(recruit.getDetail())
                .responseRate(recruit.getResponseRate())
                .jobGroup(recruit.getJobGroup())
                .position(recruit.getPosition())
                .career(recruit.getCareer())
                .location(recruit.getLocation())
                .deadline(recruit.getDeadline())
                .photos(photoUrlList)
                .views(recruit.getViews())
                .hashtags(company.getCompanyHashtags().stream()
                        .map(CompanyHashtag::getHashtag)
                        .map(Hashtag::getId)
                        .collect(Collectors.toList()))
                .techstacks(company.getCompanyTechstacks().stream()
                        .map(CompanyTechstack::getTechstack)
                        .map(Techstack::getId)
                        .collect(Collectors.toList())
                )
                .build();

    }

}
