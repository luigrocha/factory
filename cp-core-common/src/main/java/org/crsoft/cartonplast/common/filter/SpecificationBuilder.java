package org.crsoft.cartonplast.common.filter;

import org.crsoft.cartonplast.common.util.SpecificationUtil;
import org.crsoft.cartonplast.vo.req.SearchCriteriaReq;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author lpillaga on 03/07/2022
 */
@Component
public class SpecificationBuilder<T> {

    public Specification<T> buildSpecification(List<SearchCriteriaReq> params) {
        Specification<T> specification = SpecificationUtil.isGreaterThanOrIsNull();
        for (int i = 0; i < params.size(); i++) {
            specification = Specification
                    .where(specification)
                    .and(getSpecification(params.get(i)));
        }
        return specification;
    }

    public Specification<T> getSpecification(SearchCriteriaReq criteria) {
        return new Specification<T>() {
            private static final long serialVersionUID = 2089704018494438143L;

            @Override
            public Predicate toPredicate(
                    Root<T> root,
                    CriteriaQuery<?> query,
                    CriteriaBuilder criteriaBuilder) {
                switch (criteria.getOperation()) {
                    case GREATER_THAN:
                        return criteriaBuilder.greaterThan(
                                root.get(criteria.getKey()),
                                criteria.getValue().toString()
                        );
                    case LESS_THAN:
                        return criteriaBuilder.lessThan(
                                root.get(criteria.getKey()),
                                criteria.getValue().toString()
                        );
                    case GREATER_THAN_EQUAL:
                        return criteriaBuilder.greaterThanOrEqualTo(
                                root.get(criteria.getKey()),
                                criteria.getValue().toString()
                        );
                    case LESS_THAN_EQUAL:
                        return criteriaBuilder.lessThanOrEqualTo(
                                root.get(criteria.getKey()),
                                criteria.getValue().toString()
                        );
                    case NOT_EQUAL:
                        return criteriaBuilder.notEqual(
                                root.get(criteria.getKey()),
                                criteria.getValue()
                        );
                    case EQUAL:
                        return criteriaBuilder.equal(
                                root.get(criteria.getKey()),
                                criteria.getValue()
                        );
                    case LIKE:
                        return criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%");
                    case LIKE_END:
                        return criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get(criteria.getKey())), criteria.getValue().toString().toLowerCase() + "%");
                    case LIKE_START:
                        return criteriaBuilder.like(
                                criteriaBuilder.lower(
                                        root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase());
                    case IN:
                        return criteriaBuilder.in(
                                root.get(criteria.getKey())).value(criteria.getValue());
                    case NOT_IN:
                        return criteriaBuilder.not(
                                root.get(criteria.getKey())).in(criteria.getValue());
                    default:
                        return null;
                }
            }
        };
    }
}
