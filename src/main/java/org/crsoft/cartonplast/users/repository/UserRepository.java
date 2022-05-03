package org.crsoft.cartonplast.users.repository;

import org.crsoft.cartonplast.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User Repository
 *
 * @author jyepez on 2/5/2022
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUsername(String userName);
}
