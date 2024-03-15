package com.skcc.egovcore.domain.orders.vo;

import com.skcc.egovcore.core.paging.dto.Paging;
import com.skcc.egovcore.domain.orders.mapper.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class OrdersFindRequest extends Paging {

    private OrderStatus orderStatus;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    @Builder
    public OrdersFindRequest(OrderStatus orderStatus, LocalDate startDate, LocalDate endDate) {
        this.orderStatus = orderStatus;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
