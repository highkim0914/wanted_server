package com.risingtest.wanted.src.company.model;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostCompanyReq {
    private String name;

    private String location;

    private String address;

    private String registrationNumber;

    private int salesAmount;

    private String industry;

    private int employeesNumber;

    private String detail;

    private int establishmentYear;

    private String email;

    private String contactNumber;

    private String subscriptionPath;

    public Company toEntity(){
        return Company.builder()
                .name(name)
                .location(location)
                .address(address)
                .registrationNumber(registrationNumber)
                .salesAmount(salesAmount)
                .industry(industry)
                .employeesNumber(employeesNumber)
                .detail(detail)
                .establishmentYear(establishmentYear)
                .email(email)
                .contactNumber(contactNumber)
                .subscriptionPath(subscriptionPath)
                .responseRate(0.85)
                .photoUrl("")
                .profilePhotoUrl("")
                .companyHashtags(new ArrayList<>())
                .companyTechstacks(new ArrayList<>())
                .follows(new ArrayList<>())
                .recruit(new ArrayList<>())
                .users(new ArrayList<>())
                .build();
    }
}
