package com.risingtest.wanted.src.career;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.result.Result;
import com.risingtest.wanted.src.result.ResultService;
import com.risingtest.wanted.src.resume.model.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CareerService {
    @Autowired
    CareerRepository careerRepository;

    @Autowired
    ResultService resultService;

    @Transactional
    public void updateCareerByBasicCareer(List<BasicCareer> basicCareers, Resume resume) throws BaseException{
        List<Career> careers = resume.getCareers();
        for(Career career : careers){
            career.setStatus(1);
        }
        for(BasicCareer basicCareer : basicCareers){
            Career career;
            if(basicCareer.getId()!=0){
                career = careerRepository.findById(basicCareer.getId())
                        .orElseThrow(()-> new BaseException(BaseResponseStatus.NO_CAREER));
                List<Result> results = resultService.updateResultByBasicResult(basicCareer.getResults(),career);
                career.setStartDate(basicCareer.getStartDate());
                career.setEndDate(basicCareer.getEndDate());
                career.setTenure(basicCareer.getTenure());
                career.setIsInService(basicCareer.getIsInService());
                career.setCompanyName(basicCareer.getCompanyName());
                career.setStatus(0);
                career.setDepartmentPosition(basicCareer.getDepartmentPosition());
            }
            else{
                Career career1 = basicCareer.toEntity();
                career1.setResume(resume);
                career = careerRepository.save(career1);
                List<Result> results = resultService.updateResultByBasicResult(basicCareer.getResults(),career);
                career = careerRepository.save(career);
            }
        }
    }
}
