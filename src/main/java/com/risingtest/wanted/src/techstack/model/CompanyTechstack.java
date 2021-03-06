package com.risingtest.wanted.src.techstack.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.company.model.Company;
import com.risingtest.wanted.src.techstack.model.Techstack;
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
public class CompanyTechstack extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "company_id",nullable = false,updatable = false)
    @JsonBackReference
    private Company company;

    @ManyToOne
    @JoinColumn(name = "techstack_id",nullable = false,updatable = false)
    @JsonManagedReference
    private Techstack techstack;
}
