package org.crsoft.cartonplast.common.filter;

import org.crsoft.cartonplast.common.util.SpecificationUtil;
import org.crsoft.cartonplast.vo.req.SearchCriteriaReq;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
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
                String[] keys = criteria.getKey().split("\\.");
                switch (criteria.getOperation()) {
                    case LIKE_END:
                        if (keys.length == 2) {
                            return criteriaBuilder.like(
                                    criteriaBuilder.lower(
                                            root.get(keys[0]).get(keys[1])), criteria.getValue().toString().toLowerCase() + "%");
                        } else {
                            return criteriaBuilder.like(
                                    criteriaBuilder.lower(
                                            root.get(criteria.getKey())), criteria.getValue().toString().toLowerCase() + "%");
                        }
                    case LIKE:
                        if (keys.length == 2) {
                            return criteriaBuilder.like(
                                    criteriaBuilder.lower(
                                            root.get(keys[0]).get(keys[1])), "%" + criteria.getValue().toString().toLowerCase() + "%");
                        } else {
                            return criteriaBuilder.like(
                                    criteriaBuilder.lower(
                                            root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%");
                        }
                    case LIKE_START:
                        if (keys.length == 2) {
                            return criteriaBuilder.like(
                                    criteriaBuilder.lower(
                                            root.get(keys[0]).get(keys[1])), "%" + criteria.getValue().toString().toLowerCase());
                        } else {
                            return criteriaBuilder.like(
                                    criteriaBuilder.lower(
                                            root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase());
                        }
                    case LIKE_NOT:
                        if (keys.length == 2) {
                            return criteriaBuilder.notLike(
                                    criteriaBuilder.lower(
                                            root.get(keys[0]).get(keys[1])), "%" + criteria.getValue().toString().toLowerCase() + "%");
                        } else {
                            return criteriaBuilder.notLike(
                                    criteriaBuilder.lower(
                                            root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%");
                        }
                    case EQUAL:
                        if (keys.length == 2) {
                            return criteriaBuilder.equal(
                                    root.get(keys[0]).get(keys[1]),
                                    criteria.getValue()
                            );
                        } else {
                            return criteriaBuilder.equal(
                                    root.get(criteria.getKey()),
                                    criteria.getValue()
                            );
                        }
                    case NOT_EQUAL:
                        if (keys.length == 2) {
                            return criteriaBuilder.notEqual(
                                    root.get(keys[0]).get(keys[1]),
                                    criteria.getValue().toString()
                            );
                        } else {
                            return criteriaBuilder.notEqual(
                                    root.get(criteria.getKey()),
                                    criteria.getValue()
                            );
                        }
                    case DATE_AFTER:
                        LocalDateTime dateAfter = ZonedDateTime.parse(criteria.getValue().toString()).toLocalDateTime();
                        if (keys.length == 2) {
                            return criteriaBuilder.greaterThan(
                                    root.get(keys[0]).get(keys[1]),
                                    dateAfter);
                        } else {
                            return criteriaBuilder.greaterThan(
                                    root.get(criteria.getKey()),
                                    dateAfter);
                        }
                    case GREATER_THAN:
                        if (keys.length == 2) {
                            return criteriaBuilder.greaterThan(
                                    root.get(keys[0]).get(keys[1]),
                                    criteria.getValue().toString()
                            );
                        } else {
                            return criteriaBuilder.greaterThan(
                                    root.get(criteria.getKey()),
                                    criteria.getValue().toString()
                            );
                        }
                    case DATE_BEFORE:
                        LocalDateTime dateBefore = ZonedDateTime.parse(criteria.getValue().toString()).toLocalDateTime();
                        if (keys.length == 2) {
                            return criteriaBuilder.lessThan(
                                    root.get(keys[0]).get(keys[1]),
                                    dateBefore);
                        } else {
                            return criteriaBuilder.lessThan(
                                    root.get(criteria.getKey()),
                                    dateBefore);
                        }
                    case LESS_THAN:
                        if (keys.length == 2) {
                            return criteriaBuilder.lessThan(
                                    root.get(keys[0]).get(keys[1]),
                                    criteria.getValue().toString()
                            );
                        } else {
                            return criteriaBuilder.lessThan(
                                    root.get(criteria.getKey()),
                                    criteria.getValue().toString()
                            );
                        }
                    case GREATER_THAN_EQUAL:
                        if (keys.length == 2) {
                            return criteriaBuilder.greaterThanOrEqualTo(
                                    root.get(keys[0]).get(keys[1]),
                                    criteria.getValue().toString()
                            );
                        } else {
                            return criteriaBuilder.greaterThanOrEqualTo(
                                    root.get(criteria.getKey()),
                                    criteria.getValue().toString()
                            );
                        }
                    case LESS_THAN_EQUAL:
                        if (keys.length == 2) {
                            return criteriaBuilder.lessThanOrEqualTo(
                                    root.get(keys[0]).get(keys[1]),
                                    criteria.getValue().toString()
                            );
                        } else {
                            return criteriaBuilder.lessThanOrEqualTo(
                                    root.get(criteria.getKey()),
                                    criteria.getValue().toString()
                            );
                        }
                    case DATE_IS_NOT:
                        LocalDate dateIsNot = ZonedDateTime.parse(criteria.getValue().toString()).toLocalDate();
                        LocalDateTime dateTimeIsNot = LocalDateTime.of(dateIsNot, LocalTime.MIN);
                        if (keys.length == 2) {
                            return criteriaBuilder.notEqual(
                                    root.get(keys[0]).get(keys[1]),
                                    dateTimeIsNot);
                        } else {
                            return criteriaBuilder.notEqual(
                                    root.get(criteria.getKey()),
                                    dateTimeIsNot);
                        }
                    case DATE_IS:
                        LocalDate dateIs = ZonedDateTime.parse(criteria.getValue().toString()).toLocalDate();
                        LocalDateTime dateTime = LocalDateTime.of(dateIs, LocalTime.MIN);
                        if (keys.length == 2) {
                            return criteriaBuilder.equal(
                                    root.get(keys[0]).get(keys[1]),
                                    dateTime);
                        } else {
                            return criteriaBuilder.equal(
                                    root.get(criteria.getKey()),
                                    dateTime);
                        }
                    case IN:
                        if (keys.length == 2) {
                            return criteriaBuilder.in(
                                    root.get(keys[0]).get(keys[1])).value(criteria.getValue());
                        } else {
                            return criteriaBuilder.in(
                                    root.get(criteria.getKey())).value(criteria.getValue());
                        }
                    case NOT_IN:
                        if (keys.length == 2) {
                            return criteriaBuilder.not(
                                    root.get(keys[0]).get(keys[1])).in(criteria.getValue());
                        } else {
                            return criteriaBuilder.not(
                                    root.get(criteria.getKey())).in(criteria.getValue());
                        }
                    default:
                        return null;
                }
            }
        };
    }
}
