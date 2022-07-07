package org.crsoft.cartonplast.orders.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.catalog.model.CatalogPriority;
import org.crsoft.cartonplast.catalog.model.CatalogStatus;
import org.crsoft.cartonplast.catalog.repository.CatalogPriorityRepository;
import org.crsoft.cartonplast.catalog.service.impl.CatalogStatusService;
import org.crsoft.cartonplast.client.model.Client;
import org.crsoft.cartonplast.client.repository.ClientRepository;
import org.crsoft.cartonplast.common.constant.CatalogStatusConstant;
import org.crsoft.cartonplast.common.constant.OrderConstant;
import org.crsoft.cartonplast.common.exception.BusinessException;
import org.crsoft.cartonplast.common.exception.BusinessExceptionReason;
import org.crsoft.cartonplast.common.filter.SpecificationBuilder;
import org.crsoft.cartonplast.design.model.Project;
import org.crsoft.cartonplast.design.repository.ProjectRepository;
import org.crsoft.cartonplast.orders.model.Order;
import org.crsoft.cartonplast.orders.repository.OrderRepository;
import org.crsoft.cartonplast.orders.repository.specification.OrderSpecification;
import org.crsoft.cartonplast.orders.service.IOrderService;
import org.crsoft.cartonplast.orders.service.mapper.OrderMapper;
import org.crsoft.cartonplast.vo.req.CreateOrderReq;
import org.crsoft.cartonplast.vo.req.SearchCriteriaReq;
import org.crsoft.cartonplast.vo.res.GeneratedOrderCodeRes;
import org.crsoft.cartonplast.vo.res.OrderRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final ClientRepository clientRepository;
    private final ProjectRepository projectRepository;
    private final CatalogPriorityRepository catalogPriorityRepository;
    private final CatalogStatusService catalogStatusService;
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
    public OrderRes saveOrder(CreateOrderReq orderReq) {
        boolean existsByCode = this.orderRepository.existsByCode(orderReq.getCode());
        if (existsByCode) {
            log.error("Repeated order code: {}", orderReq.getCode());
            throw new BusinessException(BusinessExceptionReason.REPEATED_ORDER_CODE, orderReq.getCode());
        }

        Optional<CatalogStatus> defaultStatusOptional = this.catalogStatusService.findByTypeAndIsDefault(CatalogStatusConstant.ORDER_STATUS_CODE);
        if (defaultStatusOptional.isEmpty()) {
            log.error("Default order status not found");
            throw new BusinessException(BusinessExceptionReason.DEFAULT_ORDER_STATUS_NOT_FOUND);
        }

        Client client = this.clientRepository.findById(orderReq.getClientId())
                .orElseThrow(() -> {
                    log.error("Client not found with id: {}", orderReq.getClientId());
                    return new BusinessException(BusinessExceptionReason.CLIENT_NOT_FOUND, orderReq.getClientId());
                });

        CatalogPriority priority = this.catalogPriorityRepository.findById(orderReq.getPriorityId())
                .orElseThrow(() -> {
                    log.error("Priority not found with id: {}", orderReq.getPriorityId());
                    return new BusinessException(BusinessExceptionReason.CATALOG_PRIORITY_NOT_FOUND, orderReq.getPriorityId());
                });

        Project project = this.projectRepository.findById(orderReq.getProjectId())
                .orElseThrow(() -> {
                    log.error("Project not found with id: {}", orderReq.getProjectId());
                    return new BusinessException(BusinessExceptionReason.PROJECT_NOT_FOUND, orderReq.getProjectId());
                });

        Order order = this.orderMapper.orderReqToOrder(orderReq);
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
}
