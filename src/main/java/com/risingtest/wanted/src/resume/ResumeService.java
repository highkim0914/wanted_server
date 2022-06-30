package com.risingtest.wanted.src.resume;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.resume.model.Resume;
import com.risingtest.wanted.src.resume.model.ResumeDto;
import com.risingtest.wanted.src.user.UserProvider;
import com.risingtest.wanted.src.user.model.User;
import com.risingtest.wanted.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ResumeService {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserProvider userProvider;

    @Autowired
    ResumeRepository resumeRepository;

    @Autowired
    ResumeProvider resumeProvider;

    @Transactional
    public Resume createResume() throws BaseException {
        User user = userProvider.findUserWithUserJwtToken();
        try {
            String username = user.getUserName();

            long nextSuffix = resumeRepository.countByUserId(user.getId());

            String title = username + " " + nextSuffix;

            Resume resume = new Resume();

            resume.setTitle(title);
            resume.setName(username);
            resume.setEmail(user.getEmail());

            return resumeRepository.save(resume);
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }

    @Transactional
    public Resume updateResume(ResumeDto resumeDto) throws BaseException{
        User user = userProvider.findUserWithUserJwtToken();
        Resume resume = resumeProvider.findById(resumeDto.getId());
        if(!resume.getUser().equals(user)){
            throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
        }
        try {
            resume.setTitle(resumeDto.getTitle());
            resume.setName(resumeDto.getName());
            resume.setEmail(resumeDto.getEmail());
            resume.setPhoneNumber(resumeDto.getPhoneNumber());
            resume.setIntroduction(resume.getIntroduction());
            resume.setExternalLink(resumeDto.getExternalLink());
            resume.setCareers(resumeDto.getCareers());
            resume.setAwards(resumeDto.getAwards());
            resume.setEducations(resumeDto.getEducations());
            resume.setLanguageSkills(resumeDto.getLanguageSkills());
            return resumeRepository.save(resume);
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
