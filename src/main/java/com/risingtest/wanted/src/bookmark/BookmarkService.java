package com.risingtest.wanted.src.bookmark;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.recruit.model.Recruit;
import com.risingtest.wanted.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private BookmarkProvider bookmarkProvider;

    public Bookmark toggleBookmark(Recruit recruit, User user) throws BaseException {
        Optional<Bookmark> optionalBookmark = bookmarkRepository.findByRecruitIdAndUserId(recruit.getId(), user.getId());
        if (optionalBookmark.isPresent()){
            try {
                Bookmark bookmark = optionalBookmark.get();
                bookmark.setStatus((bookmark.getStatus() + 1) % 2);
                bookmarkRepository.save(bookmark);
                return bookmark;
            } catch (Exception e) {
                throw new BaseException(BaseResponseStatus.NO_BOOKMARK);
            }
        }
        else{
            try {
                Bookmark bookmark = Bookmark.builder()
                        .recruit(recruit)
                        .user(user)
                        .build();
                return bookmarkRepository.save(bookmark);
            }
            catch (Exception e){
                throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
            }
        }
        // 없
    }
}
