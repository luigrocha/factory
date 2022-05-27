package org.crsoft.cartonplast.common.repository;

import org.crsoft.cartonplast.common.model.TypeCellar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author jyepez on 27/5/2022
 */
@Repository
public interface TypeCellarRepository extends JpaRepository<TypeCellar, Integer> {

    Collection<TypeCellar> findAllByValidToIsNullOrderByIdAsc();

    Optional<TypeCellar> findByIdAndValidToIsNull(Integer id);

}
