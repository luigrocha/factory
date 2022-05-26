package org.crsoft.cartonplast.common.repository;

import org.crsoft.cartonplast.common.model.CatalogPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author jyepez on 25/5/2022
 */
@Repository
public interface CatalogPriorityRepository extends JpaRepository<CatalogPriority, String> {

    Collection<CatalogPriority> findAllByTypeAndValidToIsNull(String type);

}
