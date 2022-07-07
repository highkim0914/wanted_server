package com.risingtest.wanted.src.result.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.career.model.Career;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
@Where(clause = "status = 0")
public class Result extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private String detail;

    private String title;

    @ManyToOne
    @JoinColumn(name = "career_id",nullable = false,updatable = false)
    @JsonBackReference
    private Career career;
}
