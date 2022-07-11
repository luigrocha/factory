package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.ColorB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author lpillaga on 11/05/2022
 */
@Repository
public interface ColorBRepository extends JpaRepository<ColorB, String> {

    Collection<ColorB> findAllByValidToIsNullOrderByIdAsc();

    Optional<ColorB> findByIdAndValidToIsNull(String code);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM ColorA c " +
            "WHERE (c.validTo IS NULL " +
            "OR c.validTo > CURRENT_TIMESTAMP) " +
            "AND c.id = ?1")
    boolean existsByCodeAndIsNotDeleted(String code);
}
