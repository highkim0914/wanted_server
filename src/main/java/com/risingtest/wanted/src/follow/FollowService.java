package com.risingtest.wanted.src.follow;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.company.model.Company;
import com.risingtest.wanted.src.follow.model.Follow;
import com.risingtest.wanted.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowService {
    @Autowired
    FollowRepository followRepository;

    @Autowired
    FollowProvider followProvider;

    public Follow toggleFollow(Company company, User user) throws BaseException {
        Optional<Follow> optionalFollow = followRepository.findByCompanyIdAndUserId(company.getId(),user.getId());
        if (optionalFollow.isPresent()){
            try {
                Follow follow = optionalFollow.get();
                follow.setStatus((follow.getStatus() + 1) % 2);
                followRepository.save(follow);
                return follow;
            } catch (Exception e) {
                throw new BaseException(BaseResponseStatus.NO_FOLLOW);
            }
        }
        else{
            try {
                Follow follow = Follow.builder()
                        .company(company)
                        .user(user)
                        .build();
                return followRepository.save(follow);
            }
            catch (Exception e){
                throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
            }
        }
    }
}
