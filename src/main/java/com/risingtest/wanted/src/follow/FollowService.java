package com.risingtest.wanted.src.follow;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.company.model.Company;
import com.risingtest.wanted.src.follow.model.Follow;
import com.risingtest.wanted.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FollowService {
    @Autowired
    FollowRepository followRepository;

    @Autowired
    FollowProvider followProvider;

    @Transactional
    public Follow toggleFollow(Company company, User user) throws Exception {
        Follow optionalFollow = followRepository.findByCompanyIdAndUserId(company.getId(),user.getId())
                .orElseGet(()->{
                    Follow follow = Follow.builder()
                            .company(company)
                            .user(user)
                            .build();
                    follow.setStatus(1);
                    return follow;
                });
        optionalFollow.setStatus((optionalFollow.getStatus()+1)%2);
        return followRepository.save(optionalFollow);
    }

    public Follow retoggleFollow(Company company, User user) throws BaseException{
        try {
            Follow optionalFollow = followRepository.findByCompanyIdAndUserId(company.getId(),user.getId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_FOLLOW));

            optionalFollow.setStatus((optionalFollow.getStatus()+1)%2);
            return followRepository.save(optionalFollow);
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
