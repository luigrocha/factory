package org.crsoft.cartonplast.menu.repository;

import org.crsoft.cartonplast.menu.model.Menu;
import org.crsoft.cartonplast.menu.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author jyepez on 19/5/2022
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Collection<Permission> findAllByMenu(Menu menu);

    Permission findByMenuAndId(Menu menu, Integer code);

    Permission findByMenuAndRole(Menu menu, String role);
}
