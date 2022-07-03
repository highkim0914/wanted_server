package com.risingtest.wanted.src.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.bookmark.Bookmark;
import com.risingtest.wanted.src.company.model.Company;
import com.risingtest.wanted.src.follow.model.Follow;
import com.risingtest.wanted.src.jobapplication.JobApplication;
import com.risingtest.wanted.src.likemark.model.Likemark;
import com.risingtest.wanted.src.resume.model.Resume;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private long id;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
    private String photoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @JsonBackReference
    private Company company;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    @ToString.Exclude
    private List<Follow> follows;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    @ToString.Exclude
    private List<Likemark> likeMarks;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    @ToString.Exclude
    private List<Bookmark> bookmarks;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    @ToString.Exclude
    private List<Resume> resumes;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    @ToString.Exclude
    private List<JobApplication> jobApplications;

}
