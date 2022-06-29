package com.risingtest.wanted.src.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.bookmark.Bookmark;
import com.risingtest.wanted.src.company.Company;
import com.risingtest.wanted.src.follow.Follow;
import com.risingtest.wanted.src.likemark.LikeMark;
import com.risingtest.wanted.src.resume.Resume;
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

    @ManyToOne
    @JsonBackReference
    private Company company;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    @ToString.Exclude
    private List<Follow> follows;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    @ToString.Exclude
    private List<LikeMark> likeMarks;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    @ToString.Exclude
    private List<Bookmark> bookmarks;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    @ToString.Exclude
    private List<Resume> resumes;

}
