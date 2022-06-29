package com.risingtest.wanted.src.user;


import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.user.model.*;
import com.risingtest.wanted.utils.JwtService;
import com.risingtest.wanted.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public GetUserRes getUser(long id) throws BaseException {
        try {
            User user  = userRepository.findById(id)
                    .orElseThrow(()->new BaseException(USERS_EMPTY_USER_ID));
            return GetUserRes.from(user);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
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
            String jwt = jwtService.createJwt(userIdx);
            return new PostLoginRes(jwt,userIdx);
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }

    }

}
