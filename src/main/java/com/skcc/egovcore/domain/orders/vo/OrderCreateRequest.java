package com.skcc.egovcore.domain.orders.vo;

import com.skcc.egovcore.domain.items.vo.ItemDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreateRequest {
    private List<ItemDto> items = new ArrayList<>();
}
