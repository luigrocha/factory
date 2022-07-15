package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.MachineCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author jyepez on 13/7/2022
 */
@Repository
public interface MachineCatalogRepository extends JpaRepository<MachineCatalog, Integer> {

    @Query("SELECT m FROM MachineCatalog m " +
            "WHERE (m.validTo IS NULL " +
            "OR m.validTo > CURRENT_TIMESTAMP) " +
            "ORDER BY m.id ASC")
    Collection<MachineCatalog> findAllValidMachines();

}
