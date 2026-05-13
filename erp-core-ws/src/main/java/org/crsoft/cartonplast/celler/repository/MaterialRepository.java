package org.crsoft.cartonplast.celler.repository;

import org.crsoft.cartonplast.celler.model.Material;
import org.crsoft.cartonplast.celler.model.TypeMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author jyepez on 27/5/2022
 */
@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

    @Query("SELECT c FROM Material c " +
            "INNER JOIN org.crsoft.cartonplast.celler.model.CellerDetail d ON c.id = d.material.id " +
            "WHERE (c.validTo IS NULL OR " +
            "c.validTo > CURRENT_TIMESTAMP ) " +
            "AND c.typeMaterial.id = :typeMaterial " +
            "GROUP BY d.material.id " +
            "HAVING SUM(d.weight) > 0")
    Collection<Material> findAllValidByTypeMaterial(Integer typeMaterial);

    Optional<Material> findByIdAndValidToIsNull(Integer code);

    @Query("SELECT m FROM Material m " +
            "WHERE (m.validTo IS NULL OR " +
            "m.validTo > CURRENT_TIMESTAMP )")
    Collection<Material> findAllValid();

}
