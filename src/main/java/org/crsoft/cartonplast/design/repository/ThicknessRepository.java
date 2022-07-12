package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.Thickness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author lpillaga on 11/05/2022
 */
@Repository
public interface ThicknessRepository extends JpaRepository<Thickness, Integer> {

    @Query("SELECT t FROM Thickness t " +
            "WHERE (t.validTo IS NULL " +
            "OR t.validTo > CURRENT_TIMESTAMP) " +
            "ORDER BY t.weight DESC")
    Collection<Thickness> findAllValidThickness();

    Optional<Thickness> findByIdAndValidToIsNull(Integer code);

}
