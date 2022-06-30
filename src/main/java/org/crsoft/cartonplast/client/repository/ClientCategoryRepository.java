package org.crsoft.cartonplast.client.repository;

import org.crsoft.cartonplast.client.model.ClientCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lpillaga on 20/05/2022
 */
@Repository
public interface ClientCategoryRepository extends JpaRepository<ClientCategory, Integer> {
}
