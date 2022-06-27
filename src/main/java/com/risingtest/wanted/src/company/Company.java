package com.risingtest.wanted.src.company;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.recruit.Recruit;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
public class Company extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "company")
    @ToString.Exclude
    @JsonManagedReference
    private List<Recruit> recruit;

    private String name;
}
