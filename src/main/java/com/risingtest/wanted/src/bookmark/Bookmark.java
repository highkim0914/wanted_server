package com.risingtest.wanted.src.bookmark;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.recruit.model.Recruit;
import com.risingtest.wanted.src.user.model.User;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
public class Bookmark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false,updatable = false)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "recruit_id",nullable = false,updatable = false)
    @JsonBackReference
    private Recruit recruit;
}

