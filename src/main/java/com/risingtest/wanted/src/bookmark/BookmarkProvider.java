package com.risingtest.wanted.src.bookmark;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkProvider {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private JwtService jwtService;

    public List<Bookmark> findAllByUserId(long userId) throws BaseException{
        List<Bookmark> list = bookmarkRepository.findAllByUserId(userId);
        return list;
    }

    public List<Bookmark> findAllByUserToken() throws BaseException {
        return findAllByUserId(jwtService.getUserIdx());
    }
}
