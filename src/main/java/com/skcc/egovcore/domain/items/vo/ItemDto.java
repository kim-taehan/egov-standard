package com.skcc.egovcore.domain.items.vo;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class ItemDto {

    private String itemType;
    private Integer stockCount;

    @Builder
    public ItemDto(String itemType, Integer stockCount) {
        this.itemType = itemType;
        this.stockCount = stockCount;
    }

    public ItemDto() {
        log.info("call");
    }
}
