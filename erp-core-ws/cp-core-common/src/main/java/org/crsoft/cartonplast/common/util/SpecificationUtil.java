package org.crsoft.cartonplast.common.util;

import org.crsoft.cartonplast.vo.req.SearchCriteriaReq;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lpillaga on 03/07/2022
 */
public class SpecificationUtil {

    private SpecificationUtil() {
    }

    public static <T> Specification<T> isGreaterThanOrIsNull() {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.greaterThanOrEqualTo(root.get("validTo"), LocalDateTime.now()));
            predicates.add(builder.isNull(root.get("validTo")));
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static <T> Specification<T> greaterThan(SearchCriteriaReq criteria) {
        return (root, query, builder) -> builder.greaterThan(
                root.get(criteria.getKey()),
                criteria.getValue().toString()
        );
    }
}
