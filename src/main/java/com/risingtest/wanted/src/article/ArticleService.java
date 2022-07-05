package com.risingtest.wanted.src.article;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.articlecomment.ArticleComment;
import com.risingtest.wanted.src.articlecomment.ArticleCommentRepository;
import com.risingtest.wanted.src.articlecomment.PostArticleCommentReq;
import com.risingtest.wanted.src.articlelikemark.ArticleLikemark;
import com.risingtest.wanted.src.articlelikemark.ArticleLikemarkRepository;
import com.risingtest.wanted.src.articlelikemark.BasicArticleLikemark;
import com.risingtest.wanted.src.user.UserProvider;
import com.risingtest.wanted.src.user.UserService;
import com.risingtest.wanted.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleProvider articleProvider;

    @Autowired
    private UserProvider userProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleCommentRepository articleCommentRepository;

    @Autowired
    private ArticleLikemarkRepository articleLikemarkRepository;

    public Article createArticle(PostArticleReq postArticleReq) throws BaseException {
        User user = userProvider.findUserWithUserJwtToken();
        if(user.getCommunityNickname().equals("")){
            throw new BaseException(BaseResponseStatus.USER_NO_COMMUNITY_NICKNAME);
        }
        Article article = postArticleReq.toEntity(user);
        return articleRepository.save(article);
    }

    public void setUserNickname(String nickname) throws BaseException{
        User user = userProvider.findUserWithUserJwtToken();
        user.setCommunityNickname(nickname);
        userService.saveUser(user);
    }

    public ArticleComment createCommentWith(PostArticleCommentReq postArticleCommentReq) throws BaseException{
        User user = userProvider.findUserWithUserJwtToken();
        Article article = articleProvider.getArticleById(postArticleCommentReq.getArticleId());

        ArticleComment articleComment = postArticleCommentReq.toEntity(article,user);
        return articleCommentRepository.save(articleComment);
    }

    @Transactional
    public ArticleLikemark createArticleLikemarkOn(long articleId) throws BaseException{
        User user = userProvider.findUserWithUserJwtToken();
        Article article = articleProvider.getArticleById(articleId);

        ArticleLikemark articleLikemark = articleLikemarkRepository.findByArticleIdAndUserId(articleId, user.getId())
                .orElseGet(()->{
                    ArticleLikemark newArticleLikemark = new ArticleLikemark();
                    newArticleLikemark.setArticle(article);
                    newArticleLikemark.setUser(user);
                    newArticleLikemark.setStatus(1);
                    return newArticleLikemark;
                });
        articleLikemark.setStatus((articleLikemark.getStatus()+1)%2);
        return articleLikemarkRepository.save(articleLikemark);
    }

    @Transactional
    public ArticleComment deleteCommentWith(long id) throws BaseException{
        User user = userProvider.findUserWithUserJwtToken();
        ArticleComment articleComment = articleCommentRepository.findById(id)
                .orElseThrow(()->new BaseException(BaseResponseStatus.NO_ARTICLE_COMMENT));
        if(!articleComment.getUser().equals(user)){
            throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
        }
        articleComment.setStatus(1);
        return articleCommentRepository.save(articleComment);
    }
}
