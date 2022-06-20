package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.ColorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 17/06/2022
 */
@Repository
public interface ColorTypeRepository extends JpaRepository<ColorType, String> {

    @Query("SELECT c FROM ColorType c " +
            "WHERE (c.validTo IS NULL " +
            "OR c.validTo > CURRENT_TIMESTAMP) " +
            "ORDER BY c.name ASC")
    List<ColorType> findAllValidColorTypes();
}
