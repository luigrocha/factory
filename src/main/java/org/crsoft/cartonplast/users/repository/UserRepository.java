package org.crsoft.cartonplast.users.repository;

import org.crsoft.cartonplast.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User Repository
 *
 * @author jyepez on 2/5/2022
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String userName);

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<User> findByGivenUsername(String userName);

    @Query("SELECT u.person.imageName FROM User u WHERE u.username = ?1")
    String findImageNameByUsername(String userName);

    @Query("SELECT COALESCE(COUNT(u.username), 0) " +
            "FROM User u " +
            "WHERE u.username LIKE %?1%")
    int countByUsername(String userName);
}
