package org.crsoft.cartonplast.celler.repository;

import org.crsoft.cartonplast.celler.model.Celler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author jyepez on 31/5/2022
 */
@Repository
public interface CellerRepository extends JpaRepository<Celler, Integer> {

    Collection<Celler> findAllByValidToIsNullOrderByCreatedAtDesc();

    Optional<Celler> findByIdAndValidToIsNull(Integer code);

    Optional<Celler> findDistinctTopByNumberDocumentLikeAndValidToIsNullOrderByCreatedAtDesc(String numberDocument);

    @Query(value = "SELECT * FROM CDTCELL c " +
            "WHERE c.CDTCELL_NUM_DOC LIKE CONCAT(:numberDocument,'%')" +
            "ORDER BY c.CDTCELL_CREATED_AT DESC LIMIT 1", nativeQuery = true)
    Optional<Celler> findNewCodeDocumentByDocumentCode(@Param("numberDocument") String numberDocument);

    Optional<Celler> findByNumberDocumentAndValidToIsNull(String numberDocument);

    //Collection<Celler> findAllByMaterialAndValidToIsNullOrderByCreatedAtDesc(Material material);

}
