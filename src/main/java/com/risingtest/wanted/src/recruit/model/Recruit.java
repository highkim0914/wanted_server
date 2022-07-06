package com.risingtest.wanted.src.recruit.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.bookmark.model.Bookmark;
import com.risingtest.wanted.src.company.model.Company;
import com.risingtest.wanted.src.jobapplication.model.JobApplication;
import com.risingtest.wanted.src.likemark.model.Likemark;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
@Where(clause = "status = 0")
public class Recruit extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String detail;

    private double responseRate;

    private String jobGroup;

    private String position;

    private int career;

    private String location;


    private LocalDate deadline;

    private long views;

    @ManyToOne
    @JoinColumn(name = "company_id",nullable = false,updatable = false)
    @JsonBackReference
    private Company company;

    @OneToMany(mappedBy = "recruit")
    @JsonManagedReference
    @ToString.Exclude
    private List<Likemark> likeMarks;

    @OneToMany(mappedBy = "recruit")
    @JsonManagedReference
    @ToString.Exclude
    private List<Bookmark> bookmarks;

    @OneToMany(mappedBy = "recruit")
    @JsonManagedReference
    @ToString.Exclude
    private List<JobApplication> jobApplications;
}
