package org.crsoft.cartonplast.order.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.client.model.Client;
import org.crsoft.cartonplast.client.repository.ClientRepository;
import org.crsoft.cartonplast.common.exception.NotExistException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.model.CatalogPriority;
import org.crsoft.cartonplast.common.repository.CatalogPriorityRepository;
import org.crsoft.cartonplast.order.model.Order;
import org.crsoft.cartonplast.order.repository.OrderRepository;
import org.crsoft.cartonplast.order.service.IOrderService;
import org.crsoft.cartonplast.order.service.mapper.OrderMapper;
import org.crsoft.cartonplast.vo.req.CreateOrderReq;
import org.crsoft.cartonplast.vo.res.OrderRes;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jyepez on 14/5/2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final CatalogPriorityRepository catalogPriorityRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderRes> findVisibleOrders() {
        return this.orderMapper.ordersToOrdersRes(this.orderRepository.findVisibleOrders());
    }

    @Override
    public OrderRes saveOrder(CreateOrderReq orderReq) {
        Client client = this.clientRepository.findById(orderReq.getClientId())
                .orElseThrow(() -> {
                    log.error("Client not found with id: {}", orderReq.getClientId());
                    return new NotExistException("Client not found with id: " + orderReq.getClientId());
                });

        CatalogPriority priority = this.catalogPriorityRepository.findById(orderReq.getPriorityId())
                .orElseThrow(() -> {
                    log.error("Priority not found with id: {}", orderReq.getPriorityId());
                    return new NotExistException("Priority not found with id: " + orderReq.getPriorityId());
                });

        Order order = this.orderMapper.orderReqToOrder(orderReq);
        order.setClient(client);
        order.setPriority(priority);

        return null;
    }
}
