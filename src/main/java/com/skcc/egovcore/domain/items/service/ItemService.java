package com.skcc.egovcore.domain.items.service;

import com.skcc.egovcore.domain.items.vo.ItemDto;

import java.util.List;

public interface ItemService {

    int insertItems(long orderId, List<ItemDto> items);


}
