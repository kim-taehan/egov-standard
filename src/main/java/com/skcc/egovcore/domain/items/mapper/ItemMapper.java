package com.skcc.egovcore.domain.items.mapper;

import com.skcc.egovcore.core.mvc.mapper.MybatisMap;
import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ItemMapper extends EgovAbstractMapper {

    private static final String SELECT_ITEMS_BY_ORDER_ID = "ItemsMapperDao.selectItems";
    private static final String INSERT_ITEMS = "ItemsMapperDao.insertItems";

    public List<MybatisMap> selectItems(long orderId)  {
        return (List<MybatisMap>) list(SELECT_ITEMS_BY_ORDER_ID, orderId);
    }

    public int insertItem(Map<String, Object> param) {
        return insert(INSERT_ITEMS, param);
    }


}
