package com.risingtest.wanted.src.recruit;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.bookmark.BasicBookmark;
import com.risingtest.wanted.src.bookmark.Bookmark;
import com.risingtest.wanted.src.bookmark.BookmarkService;
import com.risingtest.wanted.src.recruit.model.Recruit;
import com.risingtest.wanted.src.user.UserRepository;
import com.risingtest.wanted.src.user.model.User;
import com.risingtest.wanted.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RecruitService {

    private RecruitRepository recruitRepository;

    private JwtService jwtService;

    private UserRepository userRepository;

    private BookmarkService bookmarkService;

    @Autowired
    public RecruitService(RecruitRepository recruitRepository, JwtService jwtService, UserRepository userRepository, BookmarkService bookmarkService) {
        this.recruitRepository = recruitRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.bookmarkService = bookmarkService;
    }

    @Transactional
    public BasicBookmark toggleBookmark(long recruitId) throws BaseException{
        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(()->new BaseException(BaseResponseStatus.GET_RECRUIT_NO_RECRUIT));
        long userId = jwtService.getUserIdx();
        User user = userRepository.findById(userId)
                .orElseThrow(()->new BaseException(BaseResponseStatus.USERS_EMPTY_USER_ID));

        Bookmark bookmark = bookmarkService.toggleBookmark(recruit,user);
        return BasicBookmark.from(bookmark);

    }
}
