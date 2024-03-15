package com.skcc.egovcore.core.paging.mapper;

import com.skcc.egovcore.core.paging.dto.PagingResult;
import lombok.RequiredArgsConstructor;
import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

// TODO 추후 직적 등록하는 것으로 변경
@Repository
@RequiredArgsConstructor
public class PagingMapper extends EgovAbstractMapper {

    public int pagingCall(String queryId, Map param) {
        return selectOne(queryId, param);
    }

}
