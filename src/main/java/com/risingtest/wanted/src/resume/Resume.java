package com.risingtest.wanted.src.resume;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.award.Award;
import com.risingtest.wanted.src.career.Career;
import com.risingtest.wanted.src.education.Education;
import com.risingtest.wanted.src.follow.Follow;
import com.risingtest.wanted.src.jobapplication.JobApplication;
import com.risingtest.wanted.src.language.LanguageSkill;
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
public class Resume extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false,updatable = false)
    @JsonBackReference
    private User user;

    private String phoneNumber;

    private String introduction;

    private String externalLink;

    @OneToMany(mappedBy = "resume")
    @JsonManagedReference
    @ToString.Exclude
    private List<Career> careers;

    @OneToMany(mappedBy = "resume")
    @JsonManagedReference
    @ToString.Exclude
    private List<Award> awards;

    @OneToMany(mappedBy = "resume")
    @JsonManagedReference
    @ToString.Exclude
    private List<Education> educations;

    @OneToMany(mappedBy = "resume")
    @JsonManagedReference
    @ToString.Exclude
    private List<LanguageSkill> languageSkills;

    @OneToMany(mappedBy = "resume")
    @JsonManagedReference
    @ToString.Exclude
    private List<JobApplication> jobApplications;
}
