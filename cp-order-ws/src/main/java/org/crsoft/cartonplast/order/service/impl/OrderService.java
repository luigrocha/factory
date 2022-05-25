package org.crsoft.cartonplast.order.service.impl;

import org.crsoft.cartonplast.order.model.Order;
import org.crsoft.cartonplast.order.repository.OrderRepository;
import org.crsoft.cartonplast.order.service.IOrderService;
import org.crsoft.cartonplast.order.service.mapper.OrderMapper;
import org.crsoft.cartonplast.vo.res.OrderRes;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author jyepez on 14/5/2022
 */
@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public Collection<OrderRes> findAllValidOrders() {
        return this.orderMapper.ordersToOrdersRes(this.orderRepository.findAllByValidToIsNullOrderByCreatedAtDesc());
    }

}
