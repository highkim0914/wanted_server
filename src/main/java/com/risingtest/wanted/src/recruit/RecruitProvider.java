package com.risingtest.wanted.src.recruit;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.bookmark.model.BasicBookmark;
import com.risingtest.wanted.src.bookmark.BookmarkProvider;
import com.risingtest.wanted.src.company.CompanyProvider;
import com.risingtest.wanted.src.company.model.Company;
import com.risingtest.wanted.src.likemark.LikemarkProvider;
import com.risingtest.wanted.src.recruit.model.BasicRecruitRes;
import com.risingtest.wanted.src.recruit.model.GetRecruitRes;
import com.risingtest.wanted.src.recruit.model.Recruit;
import com.risingtest.wanted.src.recruit.model.RecruitsAndBookmarksRes;
import com.risingtest.wanted.src.user.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecruitProvider {

    @Autowired
    private RecruitRepository recruitRepository;

    @Autowired
    private UserProvider userProvider;

    @Autowired
    private CompanyProvider companyProvider;

    @Autowired
    private LikemarkProvider likemarkProvider;

    @Autowired
    private BookmarkProvider bookmarkProvider;

    public RecruitsAndBookmarksRes getRecruitsWithFilter(String jobGroup, List<Integer> years, List<String> positions, List<String> locations, List<Long> hashtags, List<Long> techStacks, Pageable pageable) {
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

        List<BasicRecruitRes> list = recruitRepository.findAll(spec, pageable).stream().distinct()
                .map(BasicRecruitRes::from)
                .collect(Collectors.toList());
        try {
            List<BasicBookmark> bookmarkList = userProvider.findBasicBookmarksWithUserToken();
            return new RecruitsAndBookmarksRes(list,bookmarkList);
        }
        catch (BaseException e){
            return new RecruitsAndBookmarksRes(list, Collections.emptyList());
        }
    }

    public Recruit findRecruitById(long id) throws BaseException {
        Recruit recruit = recruitRepository.findById(id)
                .orElseThrow(()->new BaseException(BaseResponseStatus.NO_RECRUIT));
        return recruit;
    }

    @Transactional
    public GetRecruitRes getRecruitRes(long id) throws BaseException{
        Recruit recruit = findRecruitById(id);
        Company company = recruit.getCompany();
        GetRecruitRes getRecruitRes = GetRecruitRes.from(recruit,company);
        try {
            long userId = userProvider.findUserWithUserJwtToken().getId();
        }
        catch (BaseException e){
            return getRecruitRes;
        }
        getRecruitRes.setBookmarks(userProvider.findBasicBookmarksWithUserToken());
        getRecruitRes.setLikemarks(userProvider.findBasicLikemarksWithUserToken());
        getRecruitRes.setFollows(userProvider.findBasicFollowsWithUserToken());
        getRecruitRes.setCount(company.getFollows().stream()
                .filter(follow -> follow.getStatus()==0)
                .count());
        recruit.setViews(recruit.getViews()+1);
        return getRecruitRes;
    }

    public List<BasicRecruitRes> findRecruitWithLikemarks() throws BaseException{
        List<Long> recruitIds = likemarkProvider.findRecruitIdsByUserToken();

        List<Recruit> recruits = recruitRepository.findAllByIdIn(recruitIds);
        if(recruits.isEmpty()){
            return new ArrayList<>();
        }
        return recruits.stream()
                .map(BasicRecruitRes::from)
                .collect(Collectors.toList());

    }

    public List<BasicRecruitRes> findRecruitWithBookmarks() throws BaseException{
        List<Long> recruitIds = bookmarkProvider.findRecruitIdsByUserToken();

        List<Recruit> recruits = recruitRepository.findAllByIdIn(recruitIds);
        if(recruits.isEmpty()){
            return new ArrayList<>();
        }
        return recruits.stream()
                .map(BasicRecruitRes::from)
                .collect(Collectors.toList());
    }
}
