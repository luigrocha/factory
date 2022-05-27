package org.crsoft.cartonplast.common.repository;

import org.crsoft.cartonplast.common.model.CatalogCellar;
import org.crsoft.cartonplast.common.model.TypeCellar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author jyepez on 27/5/2022
 */
@Repository
public interface CatalogCellarRepository extends JpaRepository<CatalogCellar, Integer> {

    Collection<CatalogCellar> findAllByTypeCellarAndValidToIsNullOrderByNameAsc(TypeCellar typeCellar);

}
