package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.DieProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 12/06/2022
 */
@Repository
public interface DieProductRepository extends JpaRepository<DieProduct, Integer> {

    @Query("SELECT dp FROM DieProduct dp " +
            "WHERE (dp.validTo IS NULL " +
            "OR dp.validTo > CURRENT_TIMESTAMP) " +
            "ORDER BY dp.code DESC")
    List<DieProduct> findAllValidDieProducts();

    @Query("SELECT dp FROM DieProduct dp " +
            "WHERE (dp.validTo IS NULL " +
            "OR dp.validTo > CURRENT_TIMESTAMP) " +
            "AND dp.status.isVisible = true " +
            "ORDER BY dp.code DESC")
    List<DieProduct> findAllAvailableDieProducts();

    boolean existsByCode(String code);

    @Query("SELECT CASE WHEN COUNT(dp) > 0 THEN true ELSE false END " +
            "FROM DieProduct dp " +
            "WHERE (dp.validTo IS NULL " +
            "OR dp.validTo > CURRENT_TIMESTAMP) " +
            "AND dp.code = ?1")
    boolean existsByCodeAndIsNotDeleted(String code);
}
