package com.risingtest.wanted.src.hashtag;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.risingtest.wanted.config.BaseEntity;

import com.risingtest.wanted.src.company.Company;
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
public class CompanyHashtag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "company_id",nullable = false,updatable = false)
    @JsonBackReference
    private Company company;

    @ManyToOne
    @JoinColumn(name = "hashtag_id",nullable = false,updatable = false)
    @JsonManagedReference
    private Hashtag hashtag;


}
