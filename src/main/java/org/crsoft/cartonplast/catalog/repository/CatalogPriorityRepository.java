package org.crsoft.cartonplast.catalog.repository;

import org.crsoft.cartonplast.catalog.model.CatalogPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jyepez on 25/5/2022
 */
@Repository
public interface CatalogPriorityRepository extends JpaRepository<CatalogPriority, String> {

    @Query("SELECT c FROM CatalogPriority c " +
            "WHERE (c.validTo IS NULL " +
            "OR c.validTo > CURRENT_TIMESTAMP) AND " +
            "c.type = ?1 " +
            "ORDER BY c.index ASC")
    List<CatalogPriority> findAllByTypeOrderByIndex(String type);

}