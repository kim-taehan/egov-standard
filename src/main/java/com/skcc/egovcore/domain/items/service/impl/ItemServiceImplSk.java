package com.skcc.egovcore.domain.items.service.impl;

import com.skcc.egovcore.core.mvc.service.SkAbstractService;
import com.skcc.egovcore.domain.items.mapper.ItemMapper;
import com.skcc.egovcore.domain.items.service.ItemService;
import com.skcc.egovcore.domain.items.vo.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImplSk extends SkAbstractService implements ItemService {

    private final ItemMapper itemMapper;

    @Override
    public int insertItems(long orderId, List<ItemDto> items) {
        int itemCount = 0;
        for (ItemDto item : items) {
            Map<String, Object> itemParam = new HashMap<>();
            itemParam.put("orderId", orderId);
            itemParam.put("stockCount", item.getStockCount());
            itemParam.put("itemType", item.getItemType());
            itemCount += itemMapper.insertItem(itemParam);
        }
        return itemCount;
    }
}
