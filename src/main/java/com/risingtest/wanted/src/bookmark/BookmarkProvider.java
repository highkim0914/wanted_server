package com.risingtest.wanted.src.bookmark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkProvider {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    public List<Bookmark> findAllByUserId(long userId){
        List<Bookmark> list = bookmarkRepository.findAllByUserId(userId);
        return list;
    }

}
