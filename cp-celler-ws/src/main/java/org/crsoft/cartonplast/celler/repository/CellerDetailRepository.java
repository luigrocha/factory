package org.crsoft.cartonplast.celler.repository;

import org.crsoft.cartonplast.celler.model.Celler;
import org.crsoft.cartonplast.celler.model.CellerDetail;
import org.crsoft.cartonplast.celler.model.Location;
import org.crsoft.cartonplast.celler.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author jyepez on 31/5/2022
 */
@Repository
public interface CellerDetailRepository extends JpaRepository<CellerDetail, Integer> {

    Optional<CellerDetail> findByIdAndValidToIsNull(Integer code);

    Collection<CellerDetail> findAllByMaterialAndValidToIsNullOrderByCreatedAtDesc(Material material);

    Collection<CellerDetail> findAllByCellerAndValidToIsNull(Celler celler);

    Collection<CellerDetail> findAllByLocationAndMaterialAndValidToIsNull(Location location, Material material);

}
