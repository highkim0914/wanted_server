package com.risingtest.wanted.src.user;


import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.bookmark.BasicBookmark;
import com.risingtest.wanted.src.follow.model.BasicFollow;
import com.risingtest.wanted.src.jobapplication.model.BasicJobApplication;
import com.risingtest.wanted.src.likemark.model.BasicLikemark;
import com.risingtest.wanted.src.user.model.*;
import com.risingtest.wanted.utils.JwtService;
import com.risingtest.wanted.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.risingtest.wanted.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final JwtService jwtService;
    private final UserRepository userRepository;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public GetMiniUserRes getMiniUser(long id) throws BaseException {
        try {
            User user  = userRepository.findById(id)
                    .orElseThrow(()->new BaseException(USERS_EMPTY_USER_ID));
            return GetMiniUserRes.from(user);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetUserRes getUserRes(long id) throws BaseException {
        try {
            User user  = userRepository.findById(id)
                    .orElseThrow(()->new BaseException(USERS_EMPTY_USER_ID));
            return GetUserRes.from(user);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public User findUserWithUserJwtToken() throws BaseException{
        long userId = jwtService.getUserIdx();
        User user = userRepository.findById(userId)
                .orElseThrow(()->new BaseException(BaseResponseStatus.USERS_EMPTY_USER_ID));
        return user;
    }

    public boolean isAlreadyRegisteredEmail(String email) throws BaseException{
        try{
            //return userDao.checkEmail(email);
            return userRepository.findByEmail(email).isPresent();
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException{
//        User user = userDao.getPwd(postLoginReq);
        User user = userRepository.findByEmail(postLoginReq.getEmail())
                .orElseThrow(()->new BaseException(BaseResponseStatus.LOGIN_USER_NO_EMAIL));
        String encryptPwd;
        try {
            encryptPwd= SHA256.encrypt(postLoginReq.getPassword());
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if(user.getPassword().equals(encryptPwd)){
            long userIdx = user.getId();
            String photoUrl = user.getPhotoUrl();
            String jwt = jwtService.createJwt(userIdx);
            return new PostLoginRes(jwt,userIdx, photoUrl);
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }

    }

    public List<BasicBookmark> findBasicBookmarksWithUserToken() throws BaseException{
        User user = findUserWithUserJwtToken();
        List<BasicBookmark> list = user.getBookmarks().stream()
                .map(BasicBookmark::from)
                .filter(basicBookmark -> basicBookmark.getStatus()==0)
                .collect(Collectors.toList());
        return list;
    }

    public List<BasicLikemark> findBasicLikemarksWithUserToken() throws BaseException{
        User user = findUserWithUserJwtToken();
        List<BasicLikemark> list = user.getLikeMarks().stream()
                .map(BasicLikemark::from)
                .filter(basicLikemark -> basicLikemark.getStatus()==0)
                .collect(Collectors.toList());
        return list;
    }

    public List<BasicFollow> findBasicFollowsWithUserToken() throws BaseException{
        User user = findUserWithUserJwtToken();
        List<BasicFollow> list = user.getFollows().stream()
                .map(BasicFollow::from)
                .filter(basicFollow -> basicFollow.getStatus()==0)
                .collect(Collectors.toList());
        return list;
    }

    public List<BasicJobApplication> findBasicJobApplicationsWithUserToken() {
        User user = findUserWithUserJwtToken();
        List<BasicJobApplication> list = user.getJobApplications().stream()
                .filter(basicJobApplication -> basicJobApplication.getStatus()==0)
                .map(BasicJobApplication::from)
                .collect(Collectors.toList());
        return list;
    }
}
