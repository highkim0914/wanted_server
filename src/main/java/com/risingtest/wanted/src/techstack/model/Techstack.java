package com.risingtest.wanted.src.techstack.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.risingtest.wanted.config.BaseEntity;
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
    private List<CompanyTechstack> companyTechstacks;

    private String name;
}
