package org.crsoft.cartonplast.repository;

import org.crsoft.cartonplast.model.Menu;
import org.crsoft.cartonplast.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author jyepez on 19/5/2022
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission,Integer> {

    Collection<Permission> findAllByMenu(Menu menu);
}
