package org.crsoft.cartonplast.orders.repository.specification;

import org.crsoft.cartonplast.catalog.model.CatalogStatus;
import org.crsoft.cartonplast.orders.model.Order;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lpillaga on 03/07/2022
 */
public class OrderSpecification {

    private OrderSpecification() {
    }

    public static Specification<Order> filterByStates(List<CatalogStatus> states) {
        return (root, query, builder) -> {
            if (states == null || states.isEmpty()) {
                return null;
            }

            List<Predicate> predicates = new ArrayList<>();
            for (CatalogStatus state : states) {
                predicates.add(builder.or(
                        builder.equal(root.get("status").get("id"), state.getId())
                ));
            }
            query.orderBy(builder.asc(root.get("priority").get("index")), builder.asc(root.get("orderedAt")));
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
