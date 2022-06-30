package org.crsoft.cartonplast.order.repository;

import org.crsoft.cartonplast.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jyepez on 14/5/2022
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o " +
            "WHERE (o.validTo IS NULL " +
            "OR o.validTo > CURRENT_TIMESTAMP) " +
            "AND o.status.isVisible = true " +
            "ORDER BY o.priority.index DESC, o.orderedAt ASC")
    List<Order> findVisibleOrders();
}
