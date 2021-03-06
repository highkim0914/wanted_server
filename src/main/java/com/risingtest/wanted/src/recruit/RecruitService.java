package com.risingtest.wanted.src.recruit;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.bookmark.model.BasicBookmark;
import com.risingtest.wanted.src.bookmark.model.Bookmark;
import com.risingtest.wanted.src.bookmark.BookmarkService;
import com.risingtest.wanted.src.company.model.Company;
import com.risingtest.wanted.src.jobapplication.*;
import com.risingtest.wanted.src.jobapplication.model.JobApplication;
import com.risingtest.wanted.src.jobapplication.model.JobApplicationFormReq;
import com.risingtest.wanted.src.jobapplication.model.PostJobApplicationReq;
import com.risingtest.wanted.src.likemark.model.BasicLikemark;
import com.risingtest.wanted.src.likemark.model.Likemark;
import com.risingtest.wanted.src.likemark.LikemarkService;
import com.risingtest.wanted.src.recruit.model.PostRecruitReq;
import com.risingtest.wanted.src.recruit.model.Recruit;
import com.risingtest.wanted.src.resume.ResumeProvider;
import com.risingtest.wanted.src.resume.model.Resume;
import com.risingtest.wanted.src.user.UserProvider;
import com.risingtest.wanted.src.user.model.User;
import com.risingtest.wanted.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RecruitService {

    @Autowired
    private RecruitRepository recruitRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserProvider userProvider;

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private LikemarkService likemarkService;

    @Autowired
    private RecruitProvider recruitProvider;
    @Autowired
    private ResumeProvider resumeProvider;

    @Autowired
    private JobApplicationService jobApplicationService;

    @Transactional
    public BasicBookmark toggleBookmark(long recruitId) throws BaseException{
        Recruit recruit = checkRecruitId(recruitId);
        User user = userProvider.findUserWithUserJwtToken();

        try {
            Bookmark bookmark = bookmarkService.toggleBookmark(recruit,user);
            return BasicBookmark.from(bookmark);
        }
        catch (Exception e){
            Bookmark bookmark = bookmarkService.retoggleBookmark(recruit,user);
            return BasicBookmark.from(bookmark);
        }
    }


    public BasicLikemark toggleLikemark(long recruitId) {
        Recruit recruit = checkRecruitId(recruitId);
        User user = userProvider.findUserWithUserJwtToken();

        try {
            Likemark likemark = likemarkService.toggleLikemark(recruit,user);
            return BasicLikemark.from(likemark);
        }
        catch (Exception e){
            Likemark likemark = likemarkService.retoggleLikemark(recruit,user);
            return BasicLikemark.from(likemark);
        }
    }

    public Recruit checkRecruitId(long recruitId) throws BaseException{
        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(()->new BaseException(BaseResponseStatus.NO_RECRUIT));
        return recruit;
    }

    @Transactional
    public void createJobApplyWithRecruitId(PostJobApplicationReq postJobApplicationReq, long id) throws BaseException{
        try {
            Recruit recruit = recruitProvider.findRecruitById(id);
            Resume resume = resumeProvider.findById(postJobApplicationReq.getResumeId());
            if(!resume.getIsFinished()){
                throw new BaseException(BaseResponseStatus.RESUME_NOT_FINISHED);
            }
            User user = userProvider.findUserWithUserJwtToken();
            if(!resume.getUser().equals(user)){
                throw new BaseException(BaseResponseStatus.RESUME_NOT_OWNED_BY_USER);
            }
            JobApplication jobApplication = jobApplicationService.createJobApplication(postJobApplicationReq,user,resume,recruit);
        }
        catch (BaseException e){
            throw e;
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public Recruit createRecruit(PostRecruitReq postRecruitReq, Company company) throws BaseException {
        User user = userProvider.findUserWithUserJwtToken();
        if(user.getCompany().getId()!=company.getId())
            throw new BaseException(BaseResponseStatus.USER_NOT_OWNER_OF_COMPANY);
        try {
            Recruit recruit = postRecruitReq.toEntity(company);
            return recruitRepository.save(recruit);
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public Recruit updateRecruit(PostRecruitReq postRecruitReq, Company company, long recruitId) throws BaseException{
        if(userProvider.findUserWithUserJwtToken().getCompany().getId()!=company.getId())
            throw new BaseException(BaseResponseStatus.USER_NOT_OWNER_OF_COMPANY);

        Recruit recruit = recruitProvider.findRecruitById(recruitId);
        if(recruit.getCompany().getId()!=company.getId())
            throw new BaseException(BaseResponseStatus.COMPANY_NOT_OWNER_OF_RECRUIT);
        try {
            recruit.setTitle(postRecruitReq.getTitle());
            recruit.setDeadline(postRecruitReq.getDeadline());
            recruit.setDetail(postRecruitReq.getDetail());
            recruit.setJobGroup(postRecruitReq.getJobGroup());
            recruit.setPosition(postRecruitReq.getPosition());
            recruit.setCareer(postRecruitReq.getCareer());
            return recruitRepository.save(recruit);
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public JobApplicationFormReq getJobApplicationReq() throws BaseException{
        return jobApplicationService.getJobApplicationReq();
    }

    @Transactional
    public void deleteRecruit(long recruitId) throws BaseException{
        Recruit recruit = recruitProvider.findRecruitById(recruitId);
        User user = userProvider.findUserWithUserJwtToken();

        if(recruit.getCompany().getId()!=user.getCompany().getId()){
            throw new BaseException(BaseResponseStatus.USER_NOT_OWNER_OF_COMPANY);
        }
        recruit.setStatus(1);
        recruitRepository.save(recruit);
    }
}
