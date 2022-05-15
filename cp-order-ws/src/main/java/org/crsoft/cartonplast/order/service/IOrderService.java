package org.crsoft.cartonplast.order.service;

import org.crsoft.cartonplast.vo.res.OrderRes;

import java.util.Collection;

/**
 * @author jyepez on 14/5/2022
 */
public interface IOrderService {

    Collection<OrderRes> findAllValidOrders();
}
