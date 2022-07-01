package com.risingtest.wanted.src.likemark;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.likemark.model.Likemark;
import com.risingtest.wanted.src.recruit.model.Recruit;
import com.risingtest.wanted.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@Service
public class LikemarkService {
    @Autowired
    LikemarkRepository likemarkRepository;

    @Transactional
    public Likemark toggleLikemark(Recruit recruit, User user) throws Exception {
        Likemark likemark = likemarkRepository.findByRecruitIdAndUserId(recruit.getId(), user.getId())
                .orElseGet(() -> {
                    Likemark likemark1 = Likemark.builder()
                            .recruit(recruit)
                            .user(user)
                            .build();
                    likemark1.setStatus(1);
                    return likemark1;
                });
        likemark.setStatus((likemark.getStatus() + 1) % 2);
        return likemarkRepository.save(likemark);
    }

    @Transactional
    public Likemark retoggleLikemark(Recruit recruit, User user) throws BaseException{
        try {
            Likemark likemark = likemarkRepository.findByRecruitIdAndUserId(recruit.getId(), user.getId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_LIKE));

            likemark.setStatus((likemark.getStatus() + 1) % 2);
            return likemarkRepository.save(likemark);
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
