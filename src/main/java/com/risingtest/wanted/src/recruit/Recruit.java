package com.risingtest.wanted.src.recruit;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.company.Company;
import com.risingtest.wanted.src.hashtag.RecruitHashtag;
import com.risingtest.wanted.src.techstack.RecruitTechstack;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
public class Recruit extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @ManyToOne
    @JsonBackReference
    private Company company;

    @OneToMany(mappedBy = "recruit")
    @ToString.Exclude
    @JsonManagedReference
    private List<RecruitHashtag> recruitHashtags;

    private String detail;

    private double response_rate;

    private String job_group;

    private String position;

    private int career;

    @OneToMany(mappedBy = "recruit")
    @ToString.Exclude
    @JsonManagedReference
    private List<RecruitTechstack> recruitTechstacks;

    private String location;

    private LocalDate deadline;

    private String photos;

    private long views;
}
