package org.crsoft.cartonplast.orders.repository;

import org.crsoft.cartonplast.orders.model.Order;
import org.crsoft.cartonplast.orders.repository.specification.OrderSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author jyepez on 14/5/2022
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

    @Query("SELECT o FROM Order o " +
            "WHERE (o.validTo IS NULL " +
            "OR o.validTo > CURRENT_TIMESTAMP) " +
            "AND o.status.isVisible = true " +
            "ORDER BY o.priority.index DESC, o.orderedAt ASC")
    List<Order> findVisibleOrders(OrderSpecification orderSpecification);

    boolean existsByCode(String code);

    @Query("SELECT COALESCE(MAX(o.id), 0) " +
            "FROM Order o")
    int findLastOrderId();

    @Query("SELECT o FROM Order o " +
            "WHERE (o.validTo IS NULL " +
            "OR o.validTo > CURRENT_TIMESTAMP) " +
            "AND o.status.isVisible = true AND o.status.id = :status " +
            "ORDER BY o.priority.index ASC , o.orderedAt ASC")
    Collection<Order> findOrdersByStatus(String status);

    @Query("SELECT DISTINCT o FROM Order o " +
            "WHERE (o.validTo IS NULL " +
            "OR o.validTo > CURRENT_TIMESTAMP) " +
            "AND o.status.isVisible = true AND o.lot = :lot " +
            "ORDER BY o.priority.index DESC, o.orderedAt ASC")
    Optional<Order> findOrderByLot(String lot);
}
