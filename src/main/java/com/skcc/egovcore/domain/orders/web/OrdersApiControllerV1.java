package com.skcc.egovcore.domain.orders.web;

import com.skcc.egovcore.core.config.aop.annotation.CheckMethod;
import com.skcc.egovcore.domain.orders.service.OrdersService;
import com.skcc.egovcore.domain.orders.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/orders")
public class OrdersApiControllerV1 {

    private final OrdersService ordersService;

    @GetMapping
    @CheckMethod
    public List<OrdersFindResponse> findOrders(@ModelAttribute OrdersFindRequest request) {
        return ordersService.findOrders(request);
    }

    @GetMapping("/{orderNo}")
    public OrderFindResponse findOrder(@PathVariable String orderNo) {
        return ordersService.findOrder(orderNo);
    }

    @PostMapping
    public String saveItem(@RequestBody OrderCreateRequest request) {
        return ordersService.saveOrder(request);
    }

    @PatchMapping
    public String cancelItem(@RequestBody OrderCancelRequest request) {
        return ordersService.cancelOrder(request.getOrderNo(), request);
    }


}
