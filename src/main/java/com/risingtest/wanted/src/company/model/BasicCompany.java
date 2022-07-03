package com.risingtest.wanted.src.company.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BasicCompany {
    private long id;

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

    private String profilePhotoUrl;

    private List<String> photoUrls;


    public static BasicCompany from(Company company){
        return BasicCompany.builder()
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
                .profilePhotoUrl(company.getProfilePhotoUrl())
                .photoUrls(Arrays.stream(company.getPhotoUrl().split(",")).collect(Collectors.toList()))
                .build();
    }
}
