package com.risingtest.wanted.src.result;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.career.Career;
import com.risingtest.wanted.src.resume.Resume;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
public class Result extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private String detail;

    @ManyToOne
    @JoinColumn(name = "career_id",nullable = false,updatable = false)
    @JsonBackReference
    private Career career;
}
