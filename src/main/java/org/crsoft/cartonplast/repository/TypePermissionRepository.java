package org.crsoft.cartonplast.repository;

import org.crsoft.cartonplast.model.Permission;
import org.crsoft.cartonplast.model.TypePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author jyepez on 20/5/2022
 */
@Repository
public interface TypePermissionRepository extends JpaRepository<TypePermission, Integer> {
    Collection<TypePermission> findAllByPermission(Permission permission);
}
