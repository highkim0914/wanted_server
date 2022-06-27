package com.risingtest.wanted.src.techstack;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.hashtag.Hashtag;
import com.risingtest.wanted.src.recruit.Recruit;
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
public class RecruitTechstack extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "recruit_id",nullable = false,updatable = false)
    @JsonBackReference
    private Recruit recruit;

    @ManyToOne
    @JoinColumn(name = "techstack_id",nullable = false,updatable = false)
    @JsonManagedReference
    private Techstack techstack;
}
