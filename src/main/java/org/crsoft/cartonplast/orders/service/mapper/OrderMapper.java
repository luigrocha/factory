package org.crsoft.cartonplast.orders.service.mapper;

import org.crsoft.cartonplast.client.service.mapper.ClientMapper;
import org.crsoft.cartonplast.common.service.mapper.CatalogPriorityMapper;
import org.crsoft.cartonplast.common.service.mapper.CatalogStatusMapper;
import org.crsoft.cartonplast.common.service.mapper.WithoutAuditField;
import org.crsoft.cartonplast.orders.model.Order;
import org.crsoft.cartonplast.vo.req.CreateOrderReq;
import org.crsoft.cartonplast.vo.res.OrderRes;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author jyepez on 14/5/2022
 */
@Mapper(componentModel = "spring", uses = {
        ClientMapper.class,
        CatalogStatusMapper.class,
        CatalogPriorityMapper.class
})
public interface OrderMapper {

    OrderRes orderToOrderRes(Order order);

    @WithoutAuditField
    Order orderResToOrder(OrderRes orderRes);

    @WithoutAuditField
    Order orderReqToOrder(CreateOrderReq orderReq);

    List<OrderRes> ordersToOrdersRes(Collection<Order> orders);
}
