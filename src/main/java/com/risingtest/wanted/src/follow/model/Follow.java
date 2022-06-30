package com.risingtest.wanted.src.follow.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.company.model.Company;
import com.risingtest.wanted.src.user.model.User;
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
public class Follow extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false,updatable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "company_id",nullable = false,updatable = false)
    @JsonBackReference
    private Company company;

}
