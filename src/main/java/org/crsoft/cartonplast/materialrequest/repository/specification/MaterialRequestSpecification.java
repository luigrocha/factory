package org.crsoft.cartonplast.materialrequest.repository.specification;

import org.crsoft.cartonplast.catalog.model.CatalogStatus;
import org.crsoft.cartonplast.materialrequest.model.MaterialRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lpillaga on 15/07/2022
 */
public class MaterialRequestSpecification {

    private MaterialRequestSpecification() {
    }

    public static Specification<MaterialRequest> filterByStates(List<CatalogStatus> states) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (states == null || states.isEmpty()) {
                return null;
            }

            for (CatalogStatus state : states) {
                predicates.add(builder.or(
                        builder.equal(root.get("status").get("id"), state.getId())
                ));
            }
            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<MaterialRequest> filterByQuery(String searchQuery) {
        return (root, query, builder) -> {
            if (searchQuery == null || searchQuery.isEmpty()) {
                return null;
            }
            return builder.or(
                    builder.like(root.get("number"), "%" + searchQuery + "%"),
                    builder.like(root.get("documentBy"), "%" + searchQuery + "%")
            );
        };
    }
}
