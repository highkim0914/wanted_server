package com.risingtest.wanted.src.likemark;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.likemark.model.Likemark;
import com.risingtest.wanted.src.recruit.model.Recruit;
import com.risingtest.wanted.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikemarkService {
    @Autowired
    LikemarkRepository likemarkRepository;

    public Likemark toggleLikemark(Recruit recruit, User user) throws BaseException {
        Optional<Likemark> optionalLikemark = likemarkRepository.findByRecruitIdAndUserId(recruit.getId(), user.getId());
        if (optionalLikemark.isPresent()){
            try {
                Likemark likemark = optionalLikemark.get();
                likemark.setStatus((likemark.getStatus() + 1) % 2);
                likemarkRepository.save(likemark);
                return likemark;
            } catch (Exception e) {
                throw new BaseException(BaseResponseStatus.NO_LIKE_MARK);
            }
        }
        else{
            try {
                Likemark likemark = Likemark.builder()
                        .recruit(recruit)
                        .user(user)
                        .build();
                return likemarkRepository.save(likemark);
            }
            catch (Exception e){
                throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
            }
        }
        // 없
    }
}
