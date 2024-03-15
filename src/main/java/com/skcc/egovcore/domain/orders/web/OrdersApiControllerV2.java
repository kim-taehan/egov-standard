package com.skcc.egovcore.domain.orders.web;

import com.skcc.egovcore.core.response.ResEntity;
import com.skcc.egovcore.domain.orders.service.OrdersService;
import com.skcc.egovcore.domain.orders.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v2/orders")
public class OrdersApiControllerV2 {

    private final OrdersService ordersService;

    @PostMapping("/findOrders")
    public ResEntity<List<OrdersFindResponse>> findOrders(@Valid @RequestBody OrdersFindRequest request) {
        List<OrdersFindResponse> orders = ordersService.findOrders(request);
        return ResEntity.ok(orders);
    }

    @PostMapping("/findOrder")
    public ResEntity<OrderFindResponse> findOrder(@RequestBody OrderFindRequest request) {
        OrderFindResponse ret = ordersService.findOrder(request.getOrderNo());
        return ResEntity.ok(ret);
    }

    @PostMapping("/saveOrder")
    public ResEntity<String> saveOrder(@RequestBody OrderCreateRequest request) {
        String ret = ordersService.saveOrder(request);
        return ResEntity.ok(ret);
    }

    @PostMapping("/cancelOrder")
    public ResEntity<String> cancelOrder(@RequestBody OrderCancelRequest request) {
        String ret = ordersService.cancelOrder(request.getOrderNo(), request);
        return ResEntity.ok(ret);
    }


}
