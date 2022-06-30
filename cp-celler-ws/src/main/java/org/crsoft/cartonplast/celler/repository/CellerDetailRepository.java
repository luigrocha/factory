package org.crsoft.cartonplast.celler.repository;

import org.crsoft.cartonplast.celler.model.Celler;
import org.crsoft.cartonplast.celler.model.CellerDetail;
import org.crsoft.cartonplast.celler.model.Location;
import org.crsoft.cartonplast.celler.model.Material;
import org.crsoft.cartonplast.celler.vo.LoteStockVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author jyepez on 31/5/2022
 */
@Repository
public interface CellerDetailRepository extends JpaRepository<CellerDetail, Integer> {

    Optional<CellerDetail> findByIdAndValidToIsNull(Integer code);

    @Query("SELECT new org.crsoft.cartonplast.celler.vo.LoteStockVo(c.id, SUM(c.weight) ,c.lote) FROM CellerDetail c " +
            "WHERE c.material.id =:material AND c.validTo IS NULL " +
            "GROUP BY c.lote ")
    Collection<LoteStockVo> findAllLoteStockByMaterial(Integer material);

    Collection<CellerDetail> findAllByCellerAndValidToIsNull(Celler celler);

    @Query("SELECT c FROM CellerDetail c " +
            "WHERE c.material.id = :materialCode AND c.lote = :lote AND c.validTo IS NULL " +
            "ORDER BY c.createdAt ASC")
    Collection<CellerDetail> findDetailStock(Integer materialCode, String lote);

    @Query("SELECT c FROM CellerDetail c " +
            "WHERE c.material.id = :materialCode AND c.lote = :lote AND c.validTo IS NULL " +
            "GROUP BY c.location.id")
    Collection<CellerDetail> findLocationStock(Integer materialCode, String lote);

    @Query("SELECT c FROM CellerDetail c " +
            "WHERE c.material.id = :materialCode AND c.lote = :lote AND c.validTo IS NULL " +
            "ORDER BY c.createdAt ASC")
    Collection<CellerDetail> findByMaterialAndLote(Integer materialCode, String lote);

    @Query(value = "SELECT * FROM CDTCELL_DET c " +
            "JOIN CDTCAT m on m.ID_CDTCAT_CODE = c.XID_CDTCAT_ID " +
            "JOIN CDTTIP t on t.ID_CDTTIP_ID = m.XID_CDTTIP_ID " +
            "WHERE t.ID_CDTTIP_ID = :typeCode AND c.CDTCELL_DET_VALID_TO IS NULL " +
            "GROUP BY c.CDTCELL_DET_LOTE",nativeQuery = true)
    Collection<CellerDetail> findByTypeMaterialStock(Integer typeCode);

    @Query("SELECT c FROM CellerDetail c " +
            "WHERE c.material.id = :materialCode AND c.validTo IS NULL " +
            "GROUP BY c.lote")
    Collection<CellerDetail> findLoteStock(Integer materialCode);
}
