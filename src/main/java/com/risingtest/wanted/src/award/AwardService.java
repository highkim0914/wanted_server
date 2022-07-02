package com.risingtest.wanted.src.award;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.resume.model.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AwardService {
    @Autowired
    AwardRepository awardRepository;

    @Transactional
    public void updateAwardByBasicAward(List<BasicAward> basicAwards, Resume resume) throws BaseException{
        List<Award> awards = resume.getAwards();
        for(Award award : awards){
            award.setStatus(1);
        }
        for(BasicAward basicAward : basicAwards){
            Award award;
            if(basicAward.getId()!=0){
                award = awardRepository.findById(basicAward.getId())
                        .orElseThrow(()-> new BaseException(BaseResponseStatus.NO_AWARD));
                award.setDate(basicAward.getDate());
                award.setTitle(basicAward.getTitle());
                award.setDetail(basicAward.getDetail());
                award.setStatus(0);
                awardRepository.save(award);
            }
            else{
                Award award1 = basicAward.toEntity();
                award1.setResume(resume);
                award = awardRepository.save(award1);
            }
        }
    }
}
