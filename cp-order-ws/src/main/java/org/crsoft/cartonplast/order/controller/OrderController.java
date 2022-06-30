package org.crsoft.cartonplast.order.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.order.service.IOrderService;
import org.crsoft.cartonplast.vo.res.OrderRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
