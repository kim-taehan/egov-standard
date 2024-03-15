package com.skcc.egovcore.core.paging.dto;

import com.skcc.egovcore.core.mvc.mapper.MybatisMap;
import lombok.Getter;

import java.util.List;

@Getter
public class PagingResult {

    private final List<MybatisMap> contents;
    private int totalCount;

    public PagingResult(List<MybatisMap> contents) {
        this.contents = contents;
    }

    public void updateTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
