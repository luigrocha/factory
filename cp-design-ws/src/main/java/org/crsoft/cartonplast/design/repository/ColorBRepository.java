package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.ColorB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author lpillaga on 11/05/2022
 */
@Repository
public interface ColorBRepository extends JpaRepository<ColorB, String> {

    Collection<ColorB> findAllByValidToIsNullOrderByIndexAsc();

    Optional<ColorB> findByIdAndValidToIsNull(String code);

}
