package com.risingtest.wanted.src.recruit;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.bookmark.BookmarkProvider;
import com.risingtest.wanted.src.bookmark.model.BasicBookmark;
import com.risingtest.wanted.src.company.CompanyProvider;
import com.risingtest.wanted.src.company.model.Company;
import com.risingtest.wanted.src.likemark.LikemarkProvider;
import com.risingtest.wanted.src.recruit.model.*;
import com.risingtest.wanted.src.user.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyReferenceException;
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

    private PageRequest getPageRequestFrom(int size, int page, String sortString) throws BaseException{
        PageRequest pageRequest;
        if(sortString.contains(",")) {
            String[] sortInfos = sortString.split(",");
            String schema = sortInfos[0];
            String sc = sortInfos[1];
            if(!sc.equals("desc") && !sc.equals("asc")){
                throw new BaseException(BaseResponseStatus.SORT_PARAMETER_ORDER_ERROR);
            }
            Sort sort = Sort.by(Sort.Direction.fromString(sc),schema);
            pageRequest = PageRequest.of(page, size, sort);
        }
        else{
            Sort sort = Sort.by(sortString);
            pageRequest = PageRequest.of(page,size,sort);
        }
        return pageRequest;
    }

    public RecruitsAndBookmarksRes getRecruitsWithFilter(GetRecruitsReq getRecruitsReq) throws BaseException{
        PageRequest pageRequest = getPageRequestFrom(getRecruitsReq.getSize(), getRecruitsReq.getPage(), getRecruitsReq.getSortString());

        String jobGroup = getRecruitsReq.getJobGroup();
        List<Integer> years = getRecruitsReq.getYears();
        List<String> positions = getRecruitsReq.getPositions();
        List<String> locations = getRecruitsReq.getLocations();
        List<Long> hashtags = getRecruitsReq.getHashtags();
        List<Long> techStacks = getRecruitsReq.getTechstacks();
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

        List<BasicRecruitRes> list;
        try {
            Slice<Recruit> recruits = recruitRepository.findAll(spec, pageRequest);
            list = recruits.stream().distinct()
                    .map(BasicRecruitRes::from)
                    .collect(Collectors.toList());
        }
        catch (PropertyReferenceException e){
            throw new BaseException(BaseResponseStatus.SORT_PARAMETER_ERROR);
        }
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
