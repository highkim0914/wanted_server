package com.risingtest.wanted.src.bookmark;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;

@Service
public class BookmarkProvider {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private JwtService jwtService;

    public List<Bookmark> findAllByUserId(long userId) throws BaseException{
        List<Bookmark> list = bookmarkRepository.findAllByUserIdAndStatus(userId,0);
        return list;
    }

    public List<Bookmark> findAllWithUserToken() throws  BaseException{
        return findAllByUserId(jwtService.getUserIdx());
    }

    public List<Long> findRecruitIdsByUserToken() throws BaseException {
        return bookmarkRepository.findRecruitIdByUserIdAndStatus(jwtService.getUserIdx(),0);
    }
}
