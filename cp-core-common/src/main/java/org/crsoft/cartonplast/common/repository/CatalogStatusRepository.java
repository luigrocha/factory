package org.crsoft.cartonplast.common.repository;

import org.crsoft.cartonplast.common.model.CatalogStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author jyepez on 26/5/2022
 */
@Repository
public interface CatalogStatusRepository extends JpaRepository<CatalogStatus, String> {

    Collection<CatalogStatus> findAllByTypeAndValidToIsNull(String type);

    @Query("SELECT c FROM CatalogStatus c WHERE c.type = ?1 AND c.isDefault = true")
    Optional<CatalogStatus> findByTypeAndIsDefault(String type);
}
