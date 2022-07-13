package org.crsoft.cartonplast.orders.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.catalog.model.CatalogPriority;
import org.crsoft.cartonplast.catalog.model.CatalogStatus;
import org.crsoft.cartonplast.catalog.service.ICatalogStatusService;
import org.crsoft.cartonplast.catalog.service.impl.CatalogPriorityService;
import org.crsoft.cartonplast.client.model.Client;
import org.crsoft.cartonplast.client.service.IClientService;
import org.crsoft.cartonplast.common.constant.CatalogStatusConstant;
import org.crsoft.cartonplast.common.constant.OrderConstant;
import org.crsoft.cartonplast.common.exception.BusinessException;
import org.crsoft.cartonplast.common.exception.BusinessExceptionReason;
import org.crsoft.cartonplast.common.filter.SpecificationBuilder;
import org.crsoft.cartonplast.design.model.Project;
import org.crsoft.cartonplast.design.service.IProjectService;
import org.crsoft.cartonplast.orders.model.Order;
import org.crsoft.cartonplast.orders.repository.OrderRepository;
import org.crsoft.cartonplast.orders.repository.specification.OrderSpecification;
import org.crsoft.cartonplast.orders.service.IOrderService;
import org.crsoft.cartonplast.orders.service.mapper.OrderMapper;
import org.crsoft.cartonplast.vo.req.CancelOrderReq;
import org.crsoft.cartonplast.vo.req.CreateOrderReq;
import org.crsoft.cartonplast.vo.req.SearchCriteriaReq;
import org.crsoft.cartonplast.vo.req.UpdateOrderReq;
import org.crsoft.cartonplast.vo.res.GeneratedOrderCodeRes;
import org.crsoft.cartonplast.vo.res.OrderRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author jyepez on 14/5/2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final ICatalogStatusService catalogStatusService;
    private final IClientService clientService;
    private final IProjectService projectService;
    private final CatalogPriorityService catalogPriorityService;
    private final OrderMapper orderMapper;
    private final SpecificationBuilder<Order> specificationBuilder;

    @Override
    public Page<OrderRes> findVisibleOrders(
            List<SearchCriteriaReq> searchCriteria,
            Pageable pageable,
            List<String> states,
            String query) {
        List<CatalogStatus> catalogStatuses = this.catalogStatusService.findByIds(states);

        Specification<Order> orderSpecification =
                specificationBuilder.buildSpecification(searchCriteria);

        Page<Order> orders = orderRepository.findAll(
                Specification
                        .where(orderSpecification)
                        .and(OrderSpecification.filterByStates(catalogStatuses))
                        .and(OrderSpecification.filterByQuery(query)),
                pageable);

        return orders.map(orderMapper::orderToOrderRes);
    }

    @Override
    @Transactional
    public OrderRes saveOrder(CreateOrderReq createOrderReq) {
        boolean existsByCode = this.orderRepository.existsByCode(createOrderReq.getCode());
        if (existsByCode) {
            log.error("Repeated order code: {}", createOrderReq.getCode());
            throw new BusinessException(BusinessExceptionReason.REPEATED_ORDER_CODE, createOrderReq.getCode());
        }

        Optional<CatalogStatus> defaultStatusOptional = this.catalogStatusService.findByTypeAndIsDefault(CatalogStatusConstant.ORDER_STATUS_CODE);
        if (defaultStatusOptional.isEmpty()) {
            log.error("Default order status not found");
            throw new BusinessException(BusinessExceptionReason.DEFAULT_ORDER_STATUS_NOT_FOUND);
        }

        Client client = this.clientService.findClientById(createOrderReq.getClientId());
        CatalogPriority priority = this.catalogPriorityService.findPriorityById(createOrderReq.getPriorityId());
        Project project = this.projectService.findProjectById(createOrderReq.getProjectId());

        Order order = this.orderMapper.orderReqToOrder(createOrderReq);
        order.setClient(client);
        order.setPriority(priority);
        order.setProject(project);
        order.setStatus(defaultStatusOptional.get());

        return this.orderMapper.orderToOrderRes(this.orderRepository.save(order));
    }

    @Override
    public GeneratedOrderCodeRes generateNextCode() {
        int lastCode = this.orderRepository.findLastOrderId();

        String nextOrderCode = OrderConstant.ORDER_CODE_PREFIX + (lastCode + 1);
        String lastOrderCode = "";

        Order lastOrder = this.orderRepository.findById(lastCode).orElse(null);
        if (lastOrder != null) {
            lastOrderCode = lastOrder.getCode();
        }

        return GeneratedOrderCodeRes.builder()
                .nextOrderCode(nextOrderCode)
                .lastOrderCode(lastOrderCode)
                .build();
    }

    @Override
    public OrderRes findOrderById(Integer id) {
        return this.orderMapper.orderToOrderRes(this.findById(id));
    }

    @Override
    @Transactional
    public OrderRes cancelOrder(Integer id, CancelOrderReq cancelOrderReq) {
        Order order = this.findById(id);

        CatalogStatus catalogStatus = this.catalogStatusService.findById(cancelOrderReq.getStatusCode())
                .orElseThrow(() -> {
                    log.error("Catalog status not found with id: {}", cancelOrderReq.getStatusCode());
                    return new BusinessException(BusinessExceptionReason.CATALOG_STATUS_NOT_FOUND, cancelOrderReq.getStatusCode());
                });

        order.setStatus(catalogStatus);
        order.setCancellationReason(cancelOrderReq.getCancellationReason());
        order.setCanceledAt(LocalDateTime.now());

        return this.orderMapper.orderToOrderRes(this.orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderRes updateOrder(Integer id, UpdateOrderReq updateOrderReq) {
        Order order = this.findById(id);
        Client client = this.clientService.findClientById(updateOrderReq.getClientId());
        CatalogPriority priority = this.catalogPriorityService.findPriorityById(updateOrderReq.getPriorityId());
        Project project = this.projectService.findProjectById(updateOrderReq.getProjectId());

        order.setLot(updateOrderReq.getLot());
        order.setProductCode(updateOrderReq.getProductCode());
        order.setName(updateOrderReq.getName());
        order.setQuantity(updateOrderReq.getQuantity());
        order.setClientOrderCode(updateOrderReq.getClientOrderCode());
        order.setObservation(updateOrderReq.getObservation());
        order.setEstimatedDeliveryAt(updateOrderReq.getEstimatedDeliveryAt());
        order.setClient(client);
        order.setPriority(priority);
        order.setProject(project);
        order.setLastModifiedAt(LocalDateTime.now());

        return this.orderMapper.orderToOrderRes(this.orderRepository.save(order));
    }

    @Override
    public Order findById(Integer id) {
        return this.orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order not found with id: {}", id);
                    return new BusinessException(BusinessExceptionReason.ORDER_NOT_FOUND, id);
                });
    }
}
