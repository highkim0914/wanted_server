package com.risingtest.wanted.src.bookmark;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.bookmark.model.Bookmark;
import com.risingtest.wanted.src.recruit.model.Recruit;
import com.risingtest.wanted.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private BookmarkProvider bookmarkProvider;

    @Transactional
    public Bookmark toggleBookmark(Recruit recruit, User user) throws Exception {
        Bookmark optionalBookmark = bookmarkRepository.findByRecruitIdAndUserId(recruit.getId(), user.getId())
                .orElseGet(()->{
                    Bookmark bookmark = Bookmark.builder()
                            .recruit(recruit)
                            .user(user)
                            .build();
                    bookmark.setStatus(1);
                    return bookmark;
                });
        optionalBookmark.setStatus((optionalBookmark.getStatus() + 1) % 2);
        return bookmarkRepository.save(optionalBookmark);
    }

    @Transactional
    public Bookmark retoggleBookmark(Recruit recruit, User user) throws BaseException{
        try {
            Bookmark optionalBookmark = bookmarkRepository.findByRecruitIdAndUserId(recruit.getId(), user.getId())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_BOOKMARK));

            optionalBookmark.setStatus((optionalBookmark.getStatus() + 1) % 2);
            return bookmarkRepository.save(optionalBookmark);
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
