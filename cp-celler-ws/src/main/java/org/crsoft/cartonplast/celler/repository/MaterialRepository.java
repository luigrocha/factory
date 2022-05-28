package org.crsoft.cartonplast.celler.repository;

import org.crsoft.cartonplast.celler.model.Material;
import org.crsoft.cartonplast.celler.model.TypeMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author jyepez on 27/5/2022
 */
@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {


    Collection<Material> findAllByTypeCellarAndValidToIsNullOrderByNameAsc(TypeMaterial typeMaterial);

    @Query("SELECT c FROM Material c " +
            "WHERE ((c.validTo IS NULL " +
            "OR c.validTo > CURRENT_TIMESTAMP)) AND " +
            "c.getTypeMaterial.id = :id " +
            "ORDER BY c.name DESC")
    Collection<Material> findAllByType(Integer id);


}
