package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.Printer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 12/05/2022
 */
@Repository
public interface PrinterRepository extends JpaRepository<Printer, Integer> {

    @Query("SELECT p FROM Printer p " +
            "WHERE p.validTo IS NULL OR " +
            "p.validTo > CURRENT_TIMESTAMP " +
            "ORDER BY p.name ASC")
    List<Printer> findAllValidPrinters();
}
