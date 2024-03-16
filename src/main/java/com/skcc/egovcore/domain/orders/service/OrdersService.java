package com.skcc.egovcore.domain.orders.service;


import com.skcc.egovcore.domain.orders.vo.*;

import java.util.List;

public interface OrdersService {
    List<OrdersFindResponse> findOrders(OrdersFindRequest request);
    OrderFindResponse findOrder(String orderNo);
    String saveOrder(OrderCreateRequest request);
    String cancelOrder(String orderNo, OrderCancelRequest request);
}
