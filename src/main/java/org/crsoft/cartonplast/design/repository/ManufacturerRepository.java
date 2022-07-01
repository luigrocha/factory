package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 14/06/2022
 */
@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {

    @Query("SELECT m FROM Manufacturer m " +
            "WHERE (m.validTo IS NULL " +
            "OR m.validTo > CURRENT_TIMESTAMP) " +
            "ORDER BY m.name ASC")
    List<Manufacturer> findAllValidManufacturers();
}
