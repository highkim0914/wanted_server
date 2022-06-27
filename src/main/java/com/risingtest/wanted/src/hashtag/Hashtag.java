package com.risingtest.wanted.src.hashtag;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.recruit.Recruit;
import lombok.*;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
public class Hashtag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @OneToMany(mappedBy = "hashtag")
    @ToString.Exclude
    @JsonBackReference
    private List<RecruitHashtag> hashtags;

    private String name;
}
