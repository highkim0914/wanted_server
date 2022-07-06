package com.risingtest.wanted.src.company.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.follow.model.Follow;
import com.risingtest.wanted.src.hashtag.model.CompanyHashtag;
import com.risingtest.wanted.src.recruit.model.Recruit;
import com.risingtest.wanted.src.techstack.model.CompanyTechstack;
import com.risingtest.wanted.src.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
public class Company extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String location;

    private String address;

    private String registrationNumber;

    private int salesAmount;

    private String industry;

    private double responseRate;

    private int employeesNumber;

    private String detail;

    private int establishmentYear;

    private String email;

    private String contactNumber;

    private String subscriptionPath;

    private String profilePhotoUrl;

    private String photoUrl;

    @OneToMany(mappedBy = "company")
    @ToString.Exclude
    @JsonManagedReference
    private List<User> users;

    @OneToMany(mappedBy = "company")
    @ToString.Exclude
    @JsonManagedReference
    private List<Recruit> recruit;

    @OneToMany(mappedBy = "company")
    @ToString.Exclude
    @JsonManagedReference
    private List<CompanyHashtag> companyHashtags;

    @OneToMany(mappedBy = "company")
    @ToString.Exclude
    @JsonManagedReference
    private List<CompanyTechstack> companyTechstacks;

    @OneToMany(mappedBy = "company")
    @ToString.Exclude
    @JsonManagedReference
    private List<Follow> follows;
}
