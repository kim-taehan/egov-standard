
## paging 사용법

> spring 에서 제공하는 paging의 경우 Get 방식에서 쉽게 사용할 수 있지만, SI 특성상 Post 방식을 사용하기 떄문에 Spring paging이 아닌 별도의 프로세스를 제공한다.   
> Spring Aop를 사용하여 업무 개발자가 최대한 신경을 쓰지 않고 관심사를 분리할 수 있도록 하였다.


- Controller 코드로 단순하게 리스트를 반환하는 형태로 작성
```java
@PostMapping("/findOrders")
public ResEntity<List<OrdersFindResponse>> findOrders(@RequestBody OrdersFindRequest request) {
    List<OrdersFindResponse> orders = ordersService.findOrders(request);
    return ResEntity.ok(orders);
}
```

- Request vo를 Paging 클래스를 상속받는다. (다른 데이터는 비지니스 데이터만 존재한다.)
```java
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
```

- service layer 에서는 별도의 작업이 필요하지 않다
```java

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersServiceImpl extends AbstractService implements OrdersService {

    private final OrderMapper orderMapper;

    @Override
    @CheckMethod
    public List<OrdersFindResponse> findOrders(OrdersFindRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderStatus", request.getOrderStatus().name());
        params.put("startDate", request.getStartDate().atTime(0, 0, 0));
        params.put("endDate", request.getEndDate().atTime(23, 59, 59));

        List<CamelMap> ret = orderMapper.selectOrders(params);
        return ret.stream()
                .map(this::convertDto)
                .collect(Collectors.toList());
    }
}
```

- repository 에서는 list 형태로 결과를 받도록 하고 Method 위에 @PageQuery("쿼리 아이디") 를 입력해주면 된다.    
- aop 를 통해 param에 페이지 조회 조건을 입력하고 기존 쿼리아이디 + 'Count' 로 쿼리를 수행하여 결과를 세팅한다.  
```java
@Repository
public class OrderMapper extends EgovAbstractMapper {
    private static final String SELECT_ORDERS = "OrdersMapperDao.selectOrders";

    @PageQuery(SELECT_ORDERS)
    public List<CamelMap> selectOrders(Map param) {
        return (List<CamelMap>) list(SELECT_ORDERS, param);
    }
}
```

- mapper에 기존 조회쿼리 이외에도 count 쿼리도 만들어야 한다.  
- Count 쿼리 아이디 생성 방법: 조회조건 queryId + 'Count'

* CommonMapper : 공통적으로 페이지 쿼리 처리하는 sql 조각 (다른 방법으로 작성시 제거해도 된다)
> `CommonMapper.oraclePagingPrefix` : oracle paging query prefix  
> `CommonMapper.oraclePagingSuffix` : oracle paging query suffix

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="OrdersMapperDao">

    <select id="selectOrders" resultType ="camelMap" parameterType="java.util.HashMap">
        <include refid="CommonMapper.oraclePagingPrefix"/>
        SELECT ORDER_ID, ORDER_NO
        FROM ORDERS
        <where>
            <if test="orderStatus!=null">
                AND ORDER_STATUS = #{orderStatus}
            </if>
            <if test="startDate!=null and endDate!=null">
                AND MODIFIED_DATE BETWEEN #{startDate} and #{endDate}
            </if>
        </where>
        ORDER BY ORDER_ID DESC
        <include refid="CommonMapper.oraclePagingSuffix"/>
    </select>

    <select id="selectOrdersCount" resultType ="int" parameterType="java.util.HashMap">
        SELECT COUNT(*)
        FROM ORDERS
        <where>
            <if test="orderStatus!=null">
                AND ORDER_STATUS = #{orderStatus}
            </if>
            <if test="startDate!=null and endDate!=null">
                AND MODIFIED_DATE BETWEEN #{startDate} and #{endDate}
            </if>
        </where>
    </select>
</mapper>
```






