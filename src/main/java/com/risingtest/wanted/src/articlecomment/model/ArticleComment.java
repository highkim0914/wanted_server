package com.risingtest.wanted.src.articlecomment.model;

import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.article.model.Article;
import com.risingtest.wanted.src.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
@Where(clause = "status = 0")
public class ArticleComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id",nullable = false,updatable = false)
    private Article article;

    private String detail;
}
