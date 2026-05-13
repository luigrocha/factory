package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.ColorCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author jyepez on 30/5/2022
 */
@Repository
public interface ColorCatalogRepository extends JpaRepository<ColorCatalog,Integer> {

    Collection<ColorCatalog> findAllByValidToIsNull();

    Optional<ColorCatalog> findByIdAndValidToIsNull(Integer code);

}
