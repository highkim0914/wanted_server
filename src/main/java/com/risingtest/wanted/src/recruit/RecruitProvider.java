package com.risingtest.wanted.src.recruit;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.recruit.dto.BasicRecruitRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecruitProvider {

    @Autowired
    private RecruitRepository recruitRepository;

    public List<BasicRecruitRes> getRecruitsWithFilter(String jobGroup, List<Integer> years, List<String> positions, List<String> locations, List<Long> hashtags, List<Long> techStacks) {
        Specification<Recruit> spec = Specification.where(RecruitSpecification.betweenYears(years));

        if(!jobGroup.equals(""))
            spec = spec.and(RecruitSpecification.equalsToJobGroup(jobGroup));
        if(!positions.isEmpty())
            spec = spec.and(RecruitSpecification.equalsToPositions(positions));
        if(!locations.isEmpty())
            spec = spec.and(RecruitSpecification.equalsToLocations(locations));
        if(!hashtags.isEmpty())
            spec = spec.and(RecruitSpecification.containsHashtag(hashtags));
        if(!techStacks.isEmpty())
            spec = spec.and(RecruitSpecification.containsTechstack(techStacks));

        List<BasicRecruitRes> list = recruitRepository.findAll(spec).stream().distinct()
                .map(BasicRecruitRes::from)
                .collect(Collectors.toList());
        return list;
    }

    public List<BasicRecruitRes> getRecruitById(long id) throws BaseException {

        Recruit recruit = recruitRepository.findById(id)
                .orElseThrow(()->new BaseException(BaseResponseStatus.GET_RECRUIT_NO_RECRUIT));
        return List.of(BasicRecruitRes.from(recruit));
    }
}
