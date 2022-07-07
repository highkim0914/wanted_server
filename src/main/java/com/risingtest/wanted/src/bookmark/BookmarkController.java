package com.risingtest.wanted.src.bookmark;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponse;
import com.risingtest.wanted.src.bookmark.model.BasicBookmark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookmarks")
public class BookmarkController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    BookmarkProvider bookmarkProvider;

    @GetMapping()
    public BaseResponse<List<BasicBookmark>> getBookmarks(){
        logger.info("getBookmarks");
        try {
            List<BasicBookmark> basicBookmarks = bookmarkProvider.findAllWithUserToken()
                    .stream()
                    .map(BasicBookmark::from)
                    .collect(Collectors.toList());
            return new BaseResponse<>(basicBookmarks);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
