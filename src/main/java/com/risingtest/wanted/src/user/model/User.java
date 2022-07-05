package com.risingtest.wanted.src.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.article.Article;
import com.risingtest.wanted.src.articlecomment.ArticleComment;
import com.risingtest.wanted.src.articlelikemark.ArticleLikemark;
import com.risingtest.wanted.src.bookmark.Bookmark;
import com.risingtest.wanted.src.company.model.Company;
import com.risingtest.wanted.src.follow.model.Follow;
import com.risingtest.wanted.src.jobapplication.model.JobApplication;
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

    private long profilesResumeId;
    private String jobGroup;
    private String positions;
    private int career;
    private String techStacks;

    private String communityNickname;

    @Enumerated(EnumType.STRING)
    private LookingForJobStatus lookingForJob;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonBackReference
    private Company company;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    private List<Follow> follows;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    private List<Likemark> likeMarks;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    private List<Bookmark> bookmarks;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    private List<Resume> resumes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    private List<JobApplication> jobApplications;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    private List<Article> articles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    private List<ArticleComment> articleComments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    @ToString.Exclude
    private List<ArticleLikemark> articleLikemarks;


}
