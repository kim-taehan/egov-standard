## SQL 작성 규칙

+ 1 SQL 표준 
    + 1.1 SQL 문법은 대문자로 통일한다.
    + 1.2 쿼리 제일 상단에 mapper namespace + SqlId를 주석을 입력한다.
    + 1.3 Count Query 의 sqlId는 조회 sqlId+`Count`로 고정하며, 바로 다음에 위치한다.
    + 1.4 `*` 전체조회는 카운터쿼리 이외에는 사용하지 않는다. (특히 리스트 처리시)
    + 1.5 TABLE에 alias는 무조건 지정한다.
    + 1.6 응답받을 데이터는 CamelMap 으로 지정한다.(케밥->카멜로 변경)
    + 1.7 update, insert, delete 는 각각 SQL에 맞게 입력한다.
    + 1.8 중복된 SQL은 <sql> 로 제외하여 중복을 줄인다.


### 1.1 SQL 문법은 대문자로 통일한다.
> alias, 주석을 제외하고 전부 대문자로 입력한다.

### 1.2 쿼리 제일 상단에 mapper namespace + SqlId를 주석을 입력한다.
> DB 모니터링 중에 확인할 수 있도록 Log성 주석을 입력한다.
```xml
<select id="selectOrder" resultType ="camelMap" parameterType="string">
    /* OrdersMapperDao.selectOrder */
    SELECT orders.ORDER_ID, orders.ORDER_NO, orders.ORDER_STATUS,
            orders.CREATED_DATE, orders.MODIFIED_DATE
    FROM ORDERS AS orders
    WHERE orders.ORDER_NO = #{orderNo}
</select>
```

### 1.3 Count Query 의 sqlId는 조회 sqlId+`Count`로 고정하며, 바로 다음에 위치한다.

```xml
<select id="selectOrders" resultType ="camelMap" parameterType="map">
    /* OrdersMapperDao.selectOrders */
    <include refid="CommonMapper.oraclePagingPrefix"/>
    SELECT ORDER_ID, ORDER_NO
    <include refid="OrdersMapperDao.selectOrdersPiece"/>
    ORDER BY ORDER_ID DESC
    <include refid="CommonMapper.oraclePagingSuffix"/>
</select>

<select id="selectOrdersCount" resultType ="int" parameterType="map">
    /* OrdersMapperDao.selectOrdersCount */
    SELECT COUNT(*)
    <include refid="OrdersMapperDao.selectOrdersPiece"/>
</select>
```

### 1.4 `*` 전체조회는 카운터쿼리 이외에는 사용하지 않는다. (특히 리스트 처리시)
### 1.5 TABLE에 alias는 무조건 지정한다.
### 1.6 응답받을 데이터는 CamelMap 으로 지정한다.(케밥->카멜로 변경)
### 1.7 update, insert, delete 는 각각 SQL에 맞게 입력한다.
### 1.8 중복된 SQL은 <sql> 로 제외하여 중복을 줄인다.


