package com.risingtest.wanted.src.company;

import com.risingtest.wanted.src.hashtag.CompanyHashtag;

import java.util.List;
import java.util.stream.Collectors;

import com.risingtest.wanted.src.recruit.model.BasicRecruitRes;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GetCompanyRes extends BasicCompany{
    private List<BasicRecruitRes> recruit;

    private List<CompanyHashtag> companyHashtags;

    public static GetCompanyRes from(Company company){
        return GetCompanyRes.builder()
                .id(company.getId())
                .name(company.getName())
                .location(company.getLocation())
                .address(company.getAddress())
                .registrationNumber(company.getRegistrationNumber())
                .salesAmount(company.getSalesAmount())
                .industry(company.getIndustry())
                .employeesNumber(company.getEmployeesNumber())
                .detail(company.getDetail())
                .establishmentYear(company.getEstablishmentYear())
                .email(company.getEmail())
                .contactNumber(company.getContactNumber())
                .subscriptionPath(company.getSubscriptionPath())
                .photoUrl(company.getPhotoUrl())
                .recruit(company.getRecruit().stream()
                        .map(BasicRecruitRes::from)
                        .collect(Collectors.toList())
                )
                .companyHashtags(company.getCompanyHashtags())
                .build();
    }
}
