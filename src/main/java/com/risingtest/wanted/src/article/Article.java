package com.risingtest.wanted.src.article;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.risingtest.wanted.config.BaseEntity;
import com.risingtest.wanted.src.articlecomment.ArticleComment;
import com.risingtest.wanted.src.user.model.User;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
public class Article extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String detail;

    private String tags;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false,updatable = false)
    private User user;

    @OneToMany(mappedBy = "article")
    @JsonManagedReference
    @ToString.Exclude
    private List<ArticleComment> articleCommentList;
}
