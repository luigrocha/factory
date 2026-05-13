package org.crsoft.cartonplast.celler.repository;

import org.crsoft.cartonplast.celler.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author jyepez on 31/5/2022
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    Collection<Location> findAllByValidToIsNullOrderByLocationAsc();

    Optional<Location> findByIdAndValidToIsNull(Integer code);

}
