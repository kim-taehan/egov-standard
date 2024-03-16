package com.skcc.egovcore.domain.orders.service.impl;

import com.skcc.egovcore.core.config.aop.annotation.CheckMethod;
import com.skcc.egovcore.core.mvc.mapper.MybatisMap;
import com.skcc.egovcore.core.mvc.service.SkAbstractService;
import com.skcc.egovcore.domain.items.mapper.ItemMapper;
import com.skcc.egovcore.domain.items.service.ItemService;
import com.skcc.egovcore.domain.items.vo.ItemDto;
import com.skcc.egovcore.domain.orders.mapper.OrderMapper;
import com.skcc.egovcore.domain.orders.mapper.OrderStatus;
import com.skcc.egovcore.domain.orders.service.OrdersService;
import com.skcc.egovcore.domain.orders.vo.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class OrdersServiceImpl extends SkAbstractService implements OrdersService {

    private final OrderMapper orderMapper;

    private final ItemService itemService;

    private final ItemMapper itemMapper;

    @Override
    @CheckMethod
    public List<OrdersFindResponse> findOrders(OrdersFindRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderStatus", request.getOrderStatus().name());
        params.put("startDate", request.getStartDate().atTime(0, 0, 0));
        params.put("endDate", request.getEndDate().atTime(23, 59, 59));

        List<MybatisMap> ret = orderMapper.selectOrders(params);
        return ret.stream()
                .map(this::convertDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderFindResponse findOrder(String orderNo) {
        MybatisMap order = orderMapper.selectOrder(orderNo);
        long orderId = order.getLong("orderId");
        List<MybatisMap> items = itemMapper.selectItems(orderId);
        return OrderFindResponse.of(order, items);

    }

    private OrdersFindResponse convertDto(MybatisMap order) {
        return new OrdersFindResponse(order.getLong("orderId"), order.getStr("orderNo"));
    }

    @Override
    @Transactional
    public String saveOrder(OrderCreateRequest request) {
        String orderNo = UUID.randomUUID().toString();
        Map<String, Object> params = new HashMap<>();
        params.put("orderNo", orderNo);
        params.put("orderStatus", "PENDING");

        int orderInsertCount = orderMapper.insertOrder(params);
        MybatisMap order = orderMapper.selectOrder(orderNo);
        long orderId = order.getLong("orderId");

        List<ItemDto> items = request.getItems();
        int itemCount = itemService.insertItems(orderId, items);

        // 검증 로직
        if (orderInsertCount != 1 || itemCount != items.size()) {
            throw new IllegalStateException("정상적으로 등록되지 않았습니다.");
        }
        updateOrderStatus(orderNo, OrderStatus.COMPLETED);
        return orderNo;
    }


    private void updateOrderStatus(String orderNo, OrderStatus orderStatus) {
        Map<String, Object> updateStatusParam = new HashMap<>();
        updateStatusParam.put("orderNo", orderNo);
        updateStatusParam.put("orderStatus", orderStatus.name());
        orderMapper.updateItemStatus(updateStatusParam);
    }

    @Override
    @Transactional
    public String cancelOrder(String orderNo, OrderCancelRequest request) {
        log.info("취소 사유={}", request.getMessage());
        updateOrderStatus(orderNo, OrderStatus.CANCELED);
        return "ok";
    }

}
