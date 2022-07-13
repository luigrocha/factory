package org.crsoft.cartonplast.orders.service;

import org.crsoft.cartonplast.orders.model.Order;
import org.crsoft.cartonplast.vo.req.CancelOrderReq;
import org.crsoft.cartonplast.vo.req.CreateOrderReq;
import org.crsoft.cartonplast.vo.req.SearchCriteriaReq;
import org.crsoft.cartonplast.vo.req.UpdateOrderReq;
import org.crsoft.cartonplast.vo.res.GeneratedOrderCodeRes;
import org.crsoft.cartonplast.vo.res.OrderRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author jyepez on 14/5/2022
 */
public interface IOrderService {

    Page<OrderRes> findVisibleOrders(List<SearchCriteriaReq> searchCriteria, Pageable pageable, List<String> states, String query);

    OrderRes saveOrder(CreateOrderReq createOrderReq);

    GeneratedOrderCodeRes generateNextCode();

    OrderRes findOrderById(Integer id);

    OrderRes cancelOrder(Integer id, CancelOrderReq cancelOrderReq);

    OrderRes updateOrder(Integer id, UpdateOrderReq updateOrderReq);

    Order findById(Integer id);
}
