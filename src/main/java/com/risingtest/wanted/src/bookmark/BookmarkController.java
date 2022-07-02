package com.risingtest.wanted.src.bookmark;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookmarks")
public class BookmarkController {

    @Autowired
    BookmarkProvider bookmarkProvider;

    @GetMapping()
    public BaseResponse<List<BasicBookmark>> getBookmarks(){
        try {
            List<BasicBookmark> basicBookmarks = bookmarkProvider.findAllByUserToken()
                    .stream()
                    .map(BasicBookmark::from)
                    .filter(basicBookmark -> basicBookmark.getStatus()==0)
                    .collect(Collectors.toList());
            return new BaseResponse<>(basicBookmarks);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
