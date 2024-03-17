package com.skcc.egovcore.domain.orders.vo;

import com.skcc.egovcore.core.mvc.mapper.CamelMap;
import com.skcc.egovcore.domain.items.vo.ItemDto;
import com.skcc.egovcore.domain.orders.mapper.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderFindResponse {

    private final long orderId;
    private final String orderNo;
    private final OrderStatus orderStatus;
    private final String createdDate;
    private final String modifiedDate;

    private final List<ItemDto> items = new ArrayList<>();

    @Builder
    public OrderFindResponse(long orderId, String orderNo, OrderStatus orderStatus, String createdDate, String modifiedDate) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.orderStatus = orderStatus;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public void addAllItems(List<ItemDto> items) {
        this.items.addAll(items);
    }

    public static OrderFindResponse of(CamelMap order, List<CamelMap> items) {

        OrderFindResponse res = OrderFindResponse.builder()
                .orderNo(order.getStr("orderNo"))
                .orderId(order.getLong("orderId"))
                .orderStatus(OrderStatus.valueOf(order.getStr("orderStatus")))
                .createdDate(order.getStr("createdDate"))
                .modifiedDate(order.getStr("modifiedDate"))
                .build();
        res.addAllItems(convertItems(items));
        return res;
    }

    private static List<ItemDto> convertItems(List<CamelMap> items) {
        return items.stream().map(item ->
                        ItemDto.builder()
                                .itemType(item.getStr("itemType"))
                                .stockCount(item.getInt("stockCount"))
                                .build())
                .collect(Collectors.toList());
    }
}
