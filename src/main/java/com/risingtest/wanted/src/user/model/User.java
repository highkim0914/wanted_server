package com.risingtest.wanted.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int id;
    private String userName;
    private String password;
    private String email;
    private String phoneNumber;
}
