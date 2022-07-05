package org.crsoft.cartonplast.orders.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.orders.service.IOrderService;
import org.crsoft.cartonplast.vo.req.CreateOrderReq;
import org.crsoft.cartonplast.vo.req.SearchCriteriaReq;
import org.crsoft.cartonplast.vo.res.GeneratedOrderCodeRes;
import org.crsoft.cartonplast.vo.res.OrderRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author jyepez on 14/5/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/orders")
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/search")
    public ResponseEntity<Page<OrderRes>> findAllVisibleOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam List<String> states,
            @Valid @RequestBody List<SearchCriteriaReq> searchCriteria){
        Pageable paging = PageRequest.of(page, size);

        return ResponseEntity.ok(orderService.findVisibleOrders(
                searchCriteria, paging, states));
    }

    @PostMapping
    public ResponseEntity<OrderRes> createOrder(
            @Valid @RequestBody CreateOrderReq createOrderReq){
        return ResponseEntity.ok(orderService.saveOrder(createOrderReq));
    }

    @GetMapping("/generate-code")
    public ResponseEntity<GeneratedOrderCodeRes> generateNextCode(){
        return ResponseEntity.ok(orderService.generateNextCode());
    }

    @GetMapping("/findOrderByLot/{lot}")
    public ResponseEntity<OrderRes> findOrderByLot(@PathVariable("lot") String lot) throws NotFoundException {
        return ResponseEntity.ok(orderService.findOrderByLot(lot));
    }

    @GetMapping("/findOrdersByStatus/{status}")
    public ResponseEntity<Collection<OrderRes>> findOrdersByStatus(@PathVariable("status") String status)
            throws NotFoundException {
        return ResponseEntity.ok(orderService.findOrdersByStatus(status));
    }
}
