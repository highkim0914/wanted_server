package com.risingtest.wanted.src.result;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.career.Career;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultService {
    @Autowired
    ResultRepository resultRepository;

    public List<Result> updateResultByBasicResult(List<BasicResult> basicResults, Career career) throws BaseException{
        List<Result> results = career.getResults();
        for(Result result : results){
            result.setStatus(1);
        }
        for(BasicResult basicResult : basicResults){
            Result result;
            if(basicResult.getId()!=0){
                result = resultRepository.findById(basicResult.getId())
                        .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_RESULT));

                result.setStartDate(basicResult.getStartDate());
                result.setEndDate(basicResult.getEndDate());
                result.setStatus(0);
                result.setDetail(basicResult.getDetail());
            }
            else{
                result = basicResult.toEntity();
                result.setCareer(career);
            }

            results.add(resultRepository.save(result));
        }
        return results;
    }
}
