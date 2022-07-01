package com.risingtest.wanted.src.resume;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.award.BasicAward;
import com.risingtest.wanted.src.career.BasicCareer;
import com.risingtest.wanted.src.education.BasicEducation;
import com.risingtest.wanted.src.language.BasicLanguageSkill;
import com.risingtest.wanted.src.resume.model.Resume;
import com.risingtest.wanted.src.resume.model.ResumeDto;
import com.risingtest.wanted.src.user.UserProvider;
import com.risingtest.wanted.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
public class ResumeService {
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

            long nextSuffix = resumeRepository.countByUserId(user.getId()) + 1;

            String title = username + " " + nextSuffix;

            Resume resume = new Resume();

            resume.setTitle(title);
            resume.setName(username);
            resume.setEmail(user.getEmail());
            resume.setUser(user);
            resume.setIsFinished(false);
            resume.setPhoneNumber("");
            resume.setIntroduction("");
            resume.setExternalLink("");

            return resumeRepository.save(resume);
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }

    @Transactional
    public Resume updateResume(ResumeDto resumeDto, boolean isPermanent) throws BaseException{
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
            resume.setCareers(resumeDto.getCareers()
                    .stream()
                    .map(BasicCareer::toEntity)
                    .collect(Collectors.toList())
            );
            resume.setAwards(resumeDto.getAwards()
                    .stream()
                    .map(BasicAward::toEntity)
                    .collect(Collectors.toList())
            );
            resume.setEducations(resumeDto.getEducations()
                    .stream()
                    .map(BasicEducation::toEntity)
                    .collect(Collectors.toList())
            );
            resume.setLanguageSkills(resumeDto.getLanguageSkills()
                    .stream()
                    .map(BasicLanguageSkill::toEntity)
                    .collect(Collectors.toList())
            );
            if(!resume.getIsFinished()) {
                resume.setIsFinished(isPermanent);
            }
            return resumeRepository.save(resume);
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
