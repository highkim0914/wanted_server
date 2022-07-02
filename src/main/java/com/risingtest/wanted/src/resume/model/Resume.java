package com.risingtest.wanted.src.resume.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.award.Award;
import com.risingtest.wanted.src.career.BasicCareer;
import com.risingtest.wanted.src.career.Career;
import com.risingtest.wanted.src.education.Education;
import com.risingtest.wanted.src.jobapplication.JobApplication;
import com.risingtest.wanted.src.language.LanguageSkill;
import com.risingtest.wanted.src.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
public class Resume extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false,updatable = false)
    @JsonBackReference
    private User user;

    private String title;

    private String name;

    private String email;

    private String phoneNumber;

    private String introduction;

    private String externalLink;

    private Boolean isFinished;

    @OneToMany(mappedBy = "resume")
    @JsonManagedReference
    @ToString.Exclude
    @Builder.Default
    private List<Career> careers = new ArrayList<>();

    @OneToMany(mappedBy = "resume")
    @JsonManagedReference
    @ToString.Exclude
    @Builder.Default
    private List<Award> awards  = new ArrayList<>();

    @OneToMany(mappedBy = "resume")
    @JsonManagedReference
    @ToString.Exclude
    @Builder.Default
    private List<Education> educations = new ArrayList<>();

    @OneToMany(mappedBy = "resume")
    @JsonManagedReference
    @ToString.Exclude
    @Builder.Default
    private List<LanguageSkill> languageSkills = new ArrayList<>();

    @OneToMany(mappedBy = "resume")
    @JsonManagedReference
    @ToString.Exclude
    @Builder.Default
    private List<JobApplication> jobApplications = new ArrayList<>();

}
