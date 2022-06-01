package org.crsoft.cartonplast.users.repository;

import org.crsoft.cartonplast.users.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 31/05/2022
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, String> {

    @Query("SELECT g FROM Group g " +
            "WHERE g.validTo IS NULL OR " +
            "g.validTo > CURRENT_TIMESTAMP " +
            "ORDER BY g.name ASC")
    List<Group> findAllValidGroups();
}
