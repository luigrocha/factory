package org.crsoft.cartonplast.menu.repository;

import org.crsoft.cartonplast.menu.model.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Menu Repository
 *
 * @author jyepez on 5/5/2022
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    Collection<Menu> findAllByChild(Menu child);

    Collection<Menu> findAllByValidToIsNull(Sort sort);

    Optional<Menu> findByCodeAndValidToIsNull(Integer code);

    Optional<Menu> findByUrlAndValidToIsNull(String url);

}
