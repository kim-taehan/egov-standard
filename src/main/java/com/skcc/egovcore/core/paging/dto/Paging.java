package com.skcc.egovcore.core.paging.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Paging {

    private PageDto page;

    public Paging(PageDto page) {
        this.page = page;
    }
}
