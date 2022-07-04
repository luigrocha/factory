package org.crsoft.cartonplast.orders.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.catalog.model.CatalogPriority;
import org.crsoft.cartonplast.catalog.repository.CatalogPriorityRepository;
import org.crsoft.cartonplast.client.model.Client;
import org.crsoft.cartonplast.client.repository.ClientRepository;
import org.crsoft.cartonplast.common.exception.NotExistException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.orders.model.Order;
import org.crsoft.cartonplast.orders.repository.OrderRepository;
import org.crsoft.cartonplast.orders.service.IOrderService;
import org.crsoft.cartonplast.orders.service.mapper.OrderMapper;
import org.crsoft.cartonplast.vo.req.CreateOrderReq;
import org.crsoft.cartonplast.vo.res.OrderRes;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_NOT_FOUND;

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

    @Override
    public Collection<OrderRes> findOrdersByStatus(String status) throws NotFoundException {
        Collection<Order> orders = this.orderRepository.findOrdersByStatus(status);
        if(CollectionUtil.isNotEmpty(orders)){
            return this.orderMapper.ordersToOrdersRes(orders);
        }else{
            log.error("Error to findOrdersByStatus {}", status);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public OrderRes findOrderByLot(String lot) throws NotFoundException {
        Optional<Order> order = this.orderRepository.findOrderByLot(lot);
        if(order.isPresent()){
            return this.orderMapper.orderToOrderRes(order.get());
        }else{
            log.error("Error to findOrdersByLot {}", lot);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }
}
