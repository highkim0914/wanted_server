package com.risingtest.wanted.src.follow;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.src.follow.model.Follow;
import com.risingtest.wanted.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class FollowProvider {
    @Autowired
    FollowRepository followRepository;

    @Autowired
    JwtService jwtService;

    public List<Follow> findAllByUserId(long userId) throws BaseException{
        return followRepository.findAllByUserId(userId);
    }

    public List<Follow> findAllByUserToken() throws BaseException {
        return findAllByUserId(jwtService.getUserIdx());
    }
}
