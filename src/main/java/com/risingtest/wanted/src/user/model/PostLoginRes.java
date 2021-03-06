package com.risingtest.wanted.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostLoginRes {

    private String jwt;
    private long id;
    private String photoUrl;
}
