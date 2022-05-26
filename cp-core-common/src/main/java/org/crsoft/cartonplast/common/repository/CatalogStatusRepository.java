package org.crsoft.cartonplast.common.repository;

import org.crsoft.cartonplast.common.model.CatalogStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author jyepez on 26/5/2022
 */
@Repository
public interface CatalogStatusRepository extends JpaRepository<CatalogStatus, String> {

    Collection<CatalogStatus> findAllByTypeAndValidToIsNull(String type);

}
