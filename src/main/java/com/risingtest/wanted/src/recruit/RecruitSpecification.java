package com.risingtest.wanted.src.recruit;

import com.risingtest.wanted.src.hashtag.CompanyHashtag;
import com.risingtest.wanted.src.recruit.model.Recruit;
import com.risingtest.wanted.src.techstack.CompanyTechstack;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.List;

public class RecruitSpecification {

    public static Specification<Recruit> equalsToJobGroup(String jobGroup){
        return new Specification<Recruit>() {
            @Override
            public Predicate toPredicate(Root<Recruit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("jobGroup"),jobGroup);
            }
        };
    }

    public static Specification<Recruit> betweenYears(List<Integer> years){
        return new Specification<Recruit>() {
            @Override
            public Predicate toPredicate(Root<Recruit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if(years.size()==1){
                    return criteriaBuilder.equal(root.get("career"),years.get(0));
                }
                else {
                    return criteriaBuilder.between(root.get("career"), years.get(0), years.get(1));
                }
            }
        };
    }

    public static Specification<Recruit> equalsToPositions(List<String> positions){
        return new Specification<Recruit>() {
            @Override
            public Predicate toPredicate(Root<Recruit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.disjunction();
                predicate.getExpressions().add(root.get("position").in(positions));
                return predicate;
            }
        };
    }

    public static Specification<Recruit> equalsToLocations(List<String> locations) {
        return new Specification<Recruit>() {
            @Override
            public Predicate toPredicate(Root<Recruit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.disjunction();
                predicate.getExpressions().add(root.get("location").in(locations));
                return predicate;
            }
        };
    }

    public static Specification<Recruit> containsHashtag(List<Long> hashtags){
        return new Specification<Recruit>() {
            @Override
            public Predicate toPredicate(Root<Recruit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.disjunction();
                //ListJoin<Recruit, CompanyHashtag> listJoin = root.joinList("recruitHashtags",JoinType.LEFT);
                ListJoin<Recruit, CompanyHashtag> listJoin = root.join("company").joinList("companyHashtags",JoinType.LEFT);
                predicate.getExpressions().add(listJoin.get("hashtag").in(hashtags));
                return predicate;
                //return criteriaBuilder.in(listJoin.in(hashtags));
            }
        };
    }

    public static Specification<Recruit> containsTechstack(List<Long> techstacks){
        return new Specification<Recruit>() {
            @Override
            public Predicate toPredicate(Root<Recruit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.disjunction();
                ListJoin<Recruit, CompanyTechstack> listJoin = root.join("company").joinList("companyTechstacks",JoinType.LEFT);
                predicate.getExpressions().add(listJoin.get("techstack").in(techstacks));
                return predicate;
                //return criteriaBuilder.in(listJoin.in(hashtags));
            }
        };
    }
}
