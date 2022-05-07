package org.crsoft.cartonplast.core.repository;

import org.crsoft.cartonplast.core.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Menu Repository
 *
 * @author jyepez on 5/5/2022
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu,Integer> {
    Collection<Menu> findAllByChild(Menu child);
}
