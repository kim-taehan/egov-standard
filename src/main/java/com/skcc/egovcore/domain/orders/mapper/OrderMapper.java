package com.skcc.egovcore.domain.orders.mapper;

import com.skcc.egovcore.core.mvc.mapper.CamelMap;
import com.skcc.egovcore.core.paging.annotation.PageQuery;
import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class OrderMapper extends EgovAbstractMapper {

    private static final String SELECT_ORDERS = "OrdersMapperDao.selectOrders";
    private static final String SELECT_ORDER = "OrdersMapperDao.selectOrder";
    public static final String INSERT_ORDER = "OrdersMapperDao.insertOrder";
    private static final String UPDATE_ORDER_STATUS = "OrdersMapperDao.updateOrderStatus";

    @PageQuery(SELECT_ORDERS)
    public List<CamelMap> selectOrders(Map param)  {
        return (List<CamelMap>) list(SELECT_ORDERS, param);
    }

    public CamelMap selectOrder(String orderNo) {
        return selectOne(SELECT_ORDER, orderNo);
    }

    public int insertOrder(Map param) {
        return insert(INSERT_ORDER, param);
    }

    public int updateItemStatus(Map<String, Object> param) {
        return update(UPDATE_ORDER_STATUS, param);
    }
}
