package org.crsoft.cartonplast.order.service.mapper;

import org.crsoft.cartonplast.client.service.mapper.ClientMapper;
import org.crsoft.cartonplast.common.mapper.CatalogPriorityMapper;
import org.crsoft.cartonplast.common.mapper.CatalogStatusMapper;
import org.crsoft.cartonplast.common.mapper.WithoutAuditField;
import org.crsoft.cartonplast.order.model.Order;
import org.crsoft.cartonplast.vo.res.OrderRes;
import org.mapstruct.Mapper;

import java.util.Collection;

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

    Collection<OrderRes> ordersToOrdersRes(Collection<Order> orders);
}
