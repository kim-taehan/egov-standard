package com.skcc.egovcore.domain.orders.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCancelRequest {

    private String message;
    private String orderNo;

    public OrderCancelRequest(String message, String orderNo) {
        this.message = message;
        this.orderNo = orderNo;
    }
}
