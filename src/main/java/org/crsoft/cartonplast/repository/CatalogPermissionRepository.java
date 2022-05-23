package org.crsoft.cartonplast.repository;

import org.crsoft.cartonplast.model.CatalogPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jyepez on 20/5/2022
 */
@Repository
public interface CatalogPermissionRepository extends JpaRepository<CatalogPermission, Integer> {
}
