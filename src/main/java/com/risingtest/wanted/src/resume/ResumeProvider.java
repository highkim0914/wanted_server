package com.risingtest.wanted.src.resume;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.resume.model.Resume;
import com.risingtest.wanted.src.user.UserProvider;
import com.risingtest.wanted.src.user.model.User;
import com.risingtest.wanted.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeProvider {


    @Autowired
    UserProvider userProvider;

    @Autowired
    ResumeRepository resumeRepository;


    public List<Resume> findAllByUserId(Long userId) throws BaseException {
        try {
            List<Resume> resumes = resumeRepository.findAllByUserId(userId);
            return resumes;
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public List<Resume> findAllWithUserToken() throws BaseException{
        Long userId = userProvider.findUserWithUserJwtToken().getId();
        return findAllByUserId(userId);
    }

    public Resume findById(long resumeId) throws BaseException{
        User user = userProvider.findUserWithUserJwtToken();

        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(()->new BaseException(BaseResponseStatus.NO_RESUME));
        if(resume.getUser().getId() != (user.getId())){
            throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
        }
        return resume;
    }
}
