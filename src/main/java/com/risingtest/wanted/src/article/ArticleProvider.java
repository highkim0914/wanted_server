package com.risingtest.wanted.src.article;

import com.risingtest.wanted.config.BaseException;
import com.risingtest.wanted.config.BaseResponseStatus;
import com.risingtest.wanted.src.article.model.Article;
import com.risingtest.wanted.src.article.model.BasicArticle;
import com.risingtest.wanted.src.article.model.BasicArticleBoard;
import com.risingtest.wanted.src.articlecomment.model.BasicArticleComment;
import com.risingtest.wanted.src.articlelikemark.ArticleLikemarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleProvider {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleLikemarkRepository articleLikemarkRepository;

    public BasicArticleBoard getBasicArticleBoard(String tag) throws BaseException {

        List<Article> articles = getArticlesWithTag(tag);

        BasicArticleBoard basicArticleBoard = new BasicArticleBoard();
        basicArticleBoard.setBasicArticles(articles.stream()
                .map(article -> getBasicArticleById(article.getId()))
                .collect(Collectors.toList()));

        return basicArticleBoard;
    }

    public BasicArticle getBasicArticleById(long articleId) throws  BaseException{
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_ARTICLE));

        long articleLikemarkCount = articleLikemarkRepository.countByArticleIdAndStatus(articleId,0);

        List<BasicArticleComment> comments = article.getArticleCommentList().stream()
                .map(BasicArticleComment::from)
                .collect(Collectors.toList());
        BasicArticle basicArticle = BasicArticle.from(article,articleLikemarkCount,comments);
        return basicArticle;
    }

    public Article getArticleById(long id) throws BaseException{
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_ARTICLE));
        return article;
    }

    public List<Article> getArticlesWithTag(String tag){
        if(tag.equals("")){
            return articleRepository.findAll();
        }
        else{
            return articleRepository.findAllByTagsContains(tag);
        }
    }
}
