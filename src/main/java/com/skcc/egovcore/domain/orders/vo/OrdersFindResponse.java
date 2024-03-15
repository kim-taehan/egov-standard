package com.skcc.egovcore.domain.orders.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrdersFindResponse {

    private long orderId;
    private String orderName;

    @Builder
    public OrdersFindResponse(long orderId, String orderName) {
        this.orderId = orderId;
        this.orderName = orderName;
    }
}
