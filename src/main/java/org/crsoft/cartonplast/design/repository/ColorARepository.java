package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.ColorA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author lpillaga on 11/05/2022
 */
@Repository
public interface ColorARepository extends JpaRepository<ColorA, String> {

    Collection<ColorA> findAllByValidToIsNullOrderByNameAsc();

    Optional<ColorA> findByIdAndValidToIsNull(String code);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM ColorA c " +
            "WHERE (c.validTo IS NULL " +
            "OR c.validTo > CURRENT_TIMESTAMP) " +
            "AND c.id = ?1")
    boolean existsByCodeAndIsNotDeleted(String code);
}
