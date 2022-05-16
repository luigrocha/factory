package org.crsoft.cartonplast.order.repository;

import org.crsoft.cartonplast.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author jyepez on 14/5/2022
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Collection<Order> findAllByValidToIsNullOrValidToAfter(LocalDateTime to);

    @Query("SELECT c FROM Order c " +
            "ORDER BY c.createdAt DESC")
    Collection<Order> findAllInOrder();

}
