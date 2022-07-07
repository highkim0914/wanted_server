package com.risingtest.wanted.src.article;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponse;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.article.model.*;
import com.risingtest.wanted.src.articlecomment.model.ArticleComment;
import com.risingtest.wanted.src.articlecomment.model.PostArticleCommentReq;
import com.risingtest.wanted.src.articlelikemark.model.ArticleLikemark;
import com.risingtest.wanted.src.articlelikemark.model.BasicArticleLikemark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/communities")
public class CommunityController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ArticleProvider articleProvider;

    @Autowired
    private ArticleService articleService;

    @GetMapping
    public BaseResponse<BasicArticleBoard> getArticlesWithFilter(@RequestParam(defaultValue = "") String tag){
        logger.info("getArticlesWithFilter with tag : "+ tag);
        try {
            BasicArticleBoard basicArticleBoard = articleProvider.getBasicArticleBoard(tag);
            return new BaseResponse<>(basicArticleBoard);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping
    public BaseResponse<BaseResponseStatus> postArticle(@RequestBody PostArticleReq postArticleReq){
        logger.info("postArticle");
        try {
            Article article = articleService.createArticle(postArticleReq);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PatchMapping("/nicknames")
    public BaseResponse<BaseResponseStatus> postCommunityNickname(@RequestBody PatchNicknameReq patchNicknameReq){
        logger.info("postCommunityNickname");
        String nickname = patchNicknameReq.getNickname();
        if(nickname.equals("")){
            return new BaseResponse<>(BaseResponseStatus.EMPTY_COMMUNITY_NICKNAME);
        }
        try {
            articleService.setUserNickname(nickname);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/{id}")
    public BaseResponse<BasicArticle> getArticle(@PathVariable long id){
        logger.info("getArticle: {}", id);
        try {
            BasicArticle basicArticle = articleProvider.getBasicArticleById(id);
            return new BaseResponse<>(basicArticle);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/comments")
    public BaseResponse<BaseResponseStatus> postArticleComment(@RequestBody PostArticleCommentReq postArticleCommentReq){
        logger.info("postArticleComment");
        try {
            ArticleComment articleComment = articleService.createCommentWith(postArticleCommentReq);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/comments/{id}")
    public BaseResponse<BaseResponseStatus> postArticleComment(@PathVariable long id){
        logger.info("postArticleComment");
        try {
            ArticleComment articleComment = articleService.deleteCommentWith(id);
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/likes/{id}")
    public BaseResponse<BasicArticleLikemark> likeOnArticle(@PathVariable long id){
        logger.info("likeOnArticle");
        try {
            ArticleLikemark articleLikemark = articleService.createArticleLikemarkOn(id);

            return new BaseResponse<>(BasicArticleLikemark.from(articleLikemark));
        }
        catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}
