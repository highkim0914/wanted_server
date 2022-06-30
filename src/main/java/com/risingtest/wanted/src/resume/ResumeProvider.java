package com.risingtest.wanted.src.resume;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.resume.model.Resume;
import com.risingtest.wanted.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeProvider {


    @Autowired
    JwtService jwtService;

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
        Long userId = jwtService.getUserIdx();
        return findAllByUserId(userId);
    }

    public Resume findById(long resumeId) throws BaseException{
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(()->new BaseException(BaseResponseStatus.NO_RESUME));
        return resume;
    }
}
