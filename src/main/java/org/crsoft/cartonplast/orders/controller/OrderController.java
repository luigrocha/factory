package org.crsoft.cartonplast.orders.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.orders.service.IOrderService;
import org.crsoft.cartonplast.vo.req.CancelOrderReq;
import org.crsoft.cartonplast.vo.req.CreateOrderReq;
import org.crsoft.cartonplast.vo.req.SearchCriteriaReq;
import org.crsoft.cartonplast.vo.req.UpdateOrderReq;
import org.crsoft.cartonplast.vo.res.GeneratedOrderCodeRes;
import org.crsoft.cartonplast.vo.res.OrderRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
            @RequestParam(required = false) String query,
            @RequestParam List<String> states,
            @Valid @RequestBody List<SearchCriteriaReq> searchCriteria){
        Pageable paging = PageRequest.of(page, size);

        return ResponseEntity.ok(orderService.findVisibleOrders(
                searchCriteria, paging, states, query));
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

    @GetMapping("/{id}")
    public ResponseEntity<OrderRes> findOrderById(
            @PathVariable("id") Integer id){
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<OrderRes> cancelOrder(
            @PathVariable("id") Integer id,
            @Valid @RequestBody CancelOrderReq cancelOrderReq) {
        return ResponseEntity.ok(orderService.cancelOrder(id, cancelOrderReq));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderRes> updateOrder(
            @PathVariable("id") Integer id,
            @Valid @RequestBody UpdateOrderReq updateOrderReq) {
        return ResponseEntity.ok(orderService.updateOrder(id, updateOrderReq));
    }
}
