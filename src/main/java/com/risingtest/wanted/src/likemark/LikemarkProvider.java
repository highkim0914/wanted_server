package com.risingtest.wanted.src.likemark;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.src.likemark.model.Likemark;
import com.risingtest.wanted.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikemarkProvider {
    @Autowired
    LikemarkRepository likemarkRepository;

    @Autowired
    JwtService jwtService;

    public List<Likemark> findAllByUserId(long userId) throws BaseException{
        return likemarkRepository.findAllByUserId(userId);
    }

    public List<Likemark> findAllByUserToken() throws BaseException {
        return findAllByUserId(jwtService.getUserIdx());
    }
}
