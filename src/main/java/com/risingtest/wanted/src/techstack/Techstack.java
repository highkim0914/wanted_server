package com.risingtest.wanted.src.techstack;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.hashtag.RecruitHashtag;
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
public class Techstack extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @OneToMany(mappedBy = "techstack")
    @ToString.Exclude
    @JsonBackReference
    private List<RecruitTechstack> recruitTechstacks;

    private String name;
}
