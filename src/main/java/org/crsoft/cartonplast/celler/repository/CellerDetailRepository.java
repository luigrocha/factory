package org.crsoft.cartonplast.celler.repository;

import org.crsoft.cartonplast.celler.model.Celler;
import org.crsoft.cartonplast.celler.model.CellerDetail;
import org.crsoft.cartonplast.celler.vo.LoteStockVo;
import org.crsoft.cartonplast.celler.vo.AllStockVo;
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

    @Query("SELECT new org.crsoft.cartonplast.celler.vo.LoteStockVo(c.id, SUM(c.weight) ,c.lote) FROM CellerDetail c " +
            "WHERE c.material.id =:material AND c.validTo IS NULL " +
            "GROUP BY c.lote " +
            "ORDER BY SUM(c.weight) DESC ")
    Collection<LoteStockVo> findAllLoteStockByMaterial(Integer material);

    @Query("SELECT c FROM CellerDetail c " +
            "WHERE c.material.id = :materialCode AND c.validTo IS NULL " +
            "GROUP BY c.lote")
    Collection<CellerDetail> findAllLoteStock(Integer materialCode);

    @Query("SELECT new org.crsoft.cartonplast.celler.vo.AllStockVo(c.id, c.material.typeMaterial.name, c.material.name, c.lote, c.location.description, SUM(c.weight) ) FROM CellerDetail c " +
            "GROUP BY c.material.id, c.lote, c.location.id ")
    Collection<AllStockVo> findAllStock();

    @Query("SELECT new org.crsoft.cartonplast.celler.vo.AllStockVo(c.id, c.material.typeMaterial.name, c.material.name, c.lote, c.location.description, SUM(c.weight) ) FROM CellerDetail c " +
            "WHERE c.material.typeMaterial.id = :typeCode " +
            "GROUP BY c.material.id, c.lote, c.location.id ")
    Collection<AllStockVo> findByTypeMaterialStock(Integer typeCode);

    @Query("SELECT new org.crsoft.cartonplast.celler.vo.AllStockVo(c.id, c.material.typeMaterial.name, c.material.name, c.lote, c.location.description, SUM(c.weight) ) FROM CellerDetail c " +
            "WHERE c.material.id = :materialCode " +
            "GROUP BY c.material.id, c.lote, c.location.id ")
    Collection<AllStockVo> findMaterialStock(Integer materialCode);

    @Query("SELECT new org.crsoft.cartonplast.celler.vo.AllStockVo(c.id, c.material.typeMaterial.name, c.material.name, c.lote, c.location.description, SUM(c.weight) ) FROM CellerDetail c " +
            "WHERE c.material.id = :materialCode AND c.lote = :lote " +
            "GROUP BY c.material.id, c.lote, c.location.id ")
    Collection<AllStockVo> findMaterialLoteStock(Integer materialCode,String lote);

    @Query("SELECT new org.crsoft.cartonplast.celler.vo.AllStockVo(c.id, c.material.typeMaterial.name, c.material.name, c.lote, c.location.description, SUM(c.weight) ) FROM CellerDetail c " +
            "WHERE c.material.id = :materialCode ")
    Collection<AllStockVo> findTotalStockByMaterial(Integer materialCode);

}
