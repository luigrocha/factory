package org.crsoft.cartonplast.orders.service;

import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.req.CreateOrderReq;
import org.crsoft.cartonplast.vo.res.OrderRes;

import java.util.Collection;
import java.util.List;

/**
 * @author jyepez on 14/5/2022
 */
public interface IOrderService {

    List<OrderRes> findVisibleOrders();

    OrderRes saveOrder(CreateOrderReq orderReq);

    Collection<OrderRes> findOrdersByStatus(String status) throws NotFoundException;

    OrderRes findOrderByLot(String lot) throws NotFoundException;
}
