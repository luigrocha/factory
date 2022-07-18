package org.crsoft.cartonplast.materialrequest.repository;

import org.crsoft.cartonplast.materialrequest.model.MaterialRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author lpillaga on 15/07/2022
 */
@Repository
public interface MaterialRequestRepository extends JpaRepository<MaterialRequest, Integer>, JpaSpecificationExecutor<MaterialRequest> {

    @Query(value = "SELECT * FROM CFTREQMAT m " +
            "ORDER BY m.ID_CFTREQMAT_CODE DESC LIMIT 1 ", nativeQuery = true)
    MaterialRequest findLast();

    boolean existsByNumber(String number);

    MaterialRequest findByNumber(String number);
}
