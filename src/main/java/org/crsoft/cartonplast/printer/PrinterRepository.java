package org.crsoft.cartonplast.printer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author lpillaga on 12/05/2022
 */
@Repository
public interface PrinterRepository extends JpaRepository<Printer, Integer> {

    Collection<Printer> findAllByValidToIsNullOrderByNameAsc();

    Optional<Printer> findByIdAndValidToIsNull(Integer code);

}
