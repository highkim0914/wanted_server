package com.risingtest.wanted.src.career;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.result.Result;
import com.risingtest.wanted.src.resume.model.Resume;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
public class Career extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isInService;

    private String companyName;

    private String departmentPosition;

    @ManyToOne
    @JoinColumn(name = "resume_id",nullable = false,updatable = false)
    @JsonBackReference
    private Resume resume;

    @OneToMany(mappedBy = "career")
    @JsonManagedReference
    @ToString.Exclude
    private List<Result> results;
}
