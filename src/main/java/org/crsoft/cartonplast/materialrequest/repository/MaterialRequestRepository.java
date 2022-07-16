package org.crsoft.cartonplast.materialrequest.repository;

import org.crsoft.cartonplast.materialrequest.model.MaterialRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author lpillaga on 15/07/2022
 */
@Repository
public interface MaterialRequestRepository extends JpaRepository<MaterialRequest, Integer>, JpaSpecificationExecutor<MaterialRequest> {
}
