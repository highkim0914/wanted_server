package com.risingtest.wanted.src.jobapplication;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.recruit.model.Recruit;
import com.risingtest.wanted.src.resume.model.Resume;
import com.risingtest.wanted.src.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
public class JobApplication extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String recommender;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    @ManyToOne
    @JoinColumn(name = "resume_id",nullable = false,updatable = false)
    @JsonBackReference
    private Resume resume;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false,updatable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "recruit_id",nullable = false,updatable = false)
    @JsonBackReference
    private Recruit recruit;


}
