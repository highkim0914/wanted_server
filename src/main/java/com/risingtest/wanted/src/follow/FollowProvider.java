package com.risingtest.wanted.src.follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowProvider {
    @Autowired
    FollowRepository followRepository;
}
