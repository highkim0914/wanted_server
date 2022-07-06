package com.risingtest.wanted.src.education;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.education.model.BasicEducation;
import com.risingtest.wanted.src.education.model.Education;
import com.risingtest.wanted.src.resume.model.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EducationService {
    @Autowired
    EducationRepository educationRepository;

    @Transactional
    public void updateEducationByBasicEducation(List<BasicEducation> basicEducations, Resume resume) throws BaseException{
        List<Education> educations = resume.getEducations();
        for(Education education : educations){
            education.setStatus(1);
        }
        for(BasicEducation basicEducation : basicEducations){
            Education education;
            if(basicEducation.getId()!=0){
                education = educationRepository.findById(basicEducation.getId())
                        .orElseThrow(()-> new BaseException(BaseResponseStatus.NO_EDUCATION));
                education.setStartDate(basicEducation.getStartDate());
                education.setEndDate(basicEducation.getEndDate());
                education.setDetail(basicEducation.getDetail());
                education.setSchoolName(basicEducation.getSchoolName());
                education.setIsInService(basicEducation.getIsInService());
                education.setStatus(0);
                education.setMajorDegree(basicEducation.getMajorDegree());
            }
            else{
                Education education1 = basicEducation.toEntity();
                education1.setResume(resume);
                education = educationRepository.save(education1);
            }
        }
    }
}
