package org.crsoft.cartonplast.orders.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.orders.service.IOrderService;
import org.crsoft.cartonplast.vo.res.OrderRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping
    public ResponseEntity<List<OrderRes>> findAllVisibleOrders(){
        return ResponseEntity.ok(orderService.findVisibleOrders());
    }

    @GetMapping("/findOrderByLot/{lot}")
    public ResponseEntity<OrderRes> findOrderByLot(@PathVariable("lot") String lot) throws NotFoundException {
        return ResponseEntity.ok(orderService.findOrderByLot(lot));
    }

    @GetMapping("/findOrdersByStatus/{status}")
    public ResponseEntity<Collection<OrderRes>> findOrdersByStatus(@PathVariable("status") String status) throws NotFoundException {
        return ResponseEntity.ok(orderService.findOrdersByStatus(status));
    }
}
