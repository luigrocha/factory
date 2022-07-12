package org.crsoft.cartonplast.orders.service;

import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.req.CreateOrderReq;
import org.crsoft.cartonplast.vo.req.SearchCriteriaReq;
import org.crsoft.cartonplast.vo.res.GeneratedOrderCodeRes;
import org.crsoft.cartonplast.vo.res.OrderRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

/**
 * @author jyepez on 14/5/2022
 */
public interface IOrderService {

    Page<OrderRes> findVisibleOrders(List<SearchCriteriaReq> searchCriteria, Pageable pageable, List<String> states, String query);

    OrderRes saveOrder(CreateOrderReq orderReq);

    Collection<OrderRes> findOrdersByStatus(String status) throws NotFoundException;

    OrderRes findOrderByLot(String lot) throws NotFoundException;

    GeneratedOrderCodeRes generateNextCode();
}
