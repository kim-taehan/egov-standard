package com.skcc.egovcore.domain.orders.vo;

import com.skcc.egovcore.domain.orders.mapper.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class OrderFindRequest {

    private String orderNo;

    @Builder
    public OrderFindRequest(String orderNo) {
        this.orderNo = orderNo;
    }
}
