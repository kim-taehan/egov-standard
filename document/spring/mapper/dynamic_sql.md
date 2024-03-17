## MyBatis 동적쿼리

### 1 단일 조건 `<if>`
- 조건에 따라 `<if>` 아래 sql이 포함될 수도 포함되지 않을 수 있다.  
- 주의할 점이 `<if>` 이 없어도 정상적인 SQL 문이 되어야 한다. if-else 를 처리할 수 없다

```xml
<select id="selectOrder" resultType ="camelMap" parameterType="string">
  /* OrdersMapperDao.selectOrder */
  SELECT orders.ORDER_ID, 
    orders.ORDER_NO 
  FROM ORDERS AS orders
  WHERE orders.ORDER_NO = #{orderNo}
  <if test='orderId != null and orderId != ""'>
    AND orders.ORDER_ID = #{orderId}
  </if>
</select>
```

### 2 다중 조건 `<choose>`, `<when>`, `<otherwise>`
- java의 if-else or switch 문처럼 반드시 1개의 만족하는 문법이 필요한 경우 `<if>` 대신 `<choose>` 문법을 사용하자

```xml
<select id="selectOrder" resultType ="camelMap" parameterType="string">
  /* OrdersMapperDao.selectOrder */
  SELECT orders.ORDER_ID,
    orders.ORDER_NO
  FROM ORDERS AS orders
  WHERE orders.ORDER_NO = #{orderNo}
  <choose>
    <when test='orderId != null and orderId != ""'>
      AND orders.ORDER_ID = #{orderId}
    </when>
    <otherwise>
      AND orders.ORDER_ID IS NULL
    </otherwise>
  </choose>
</select>
```

- 아래 조건처럼 반드시 `FROM TEMP_ORDERS AS orders` or `FROM ORDERS AS orders` 중에 1개가 필요한 경우 `<if>` 사용하지 말자
```xml
<select id="selectOrder" resultType ="camelMap" parameterType="string">
  /* OrdersMapperDao.selectOrder */
  SELECT orders.ORDER_ID,
    orders.ORDER_NO
  <choose>
    <when test='profile == "test"'>
      FROM TEMP_ORDERS AS orders
    </when>
    <otherwise>
      FROM ORDERS AS orders
    </otherwise>
  </choose>
  WHERE orders.ORDER_NO = #{orderNo}
</select>
```

### 3 `<where>`
- `<where>` 내부에는 조건을 표현할 수 있는 `<if>`나 `<choose>`가 사용될 수 있습니다.
- `<where>` 내부 코드가 추가되는 경우 동적으로 WHERE 키워드를 붙이고, 가장 앞에 해당되는 "AND"나 "OR"를 지워줍니다.
- WHERE 1=1 대신 `<where>` 를 사용하자
```xml
<select id="selectOrder" resultType ="camelMap" parameterType="string">
    /* OrdersMapperDao.selectOrder */
    SELECT orders.ORDER_ID,
        orders.ORDER_NO 
    FROM ORDERS AS orders
    <where>
        <if test='orderNo != null and orderNo != ""'>
            AND orders.ORDER_NO = #{orderNo}
        </if>
        <if test='orderId != null and orderId != ""'>
            AND orders.ORDER_ID = #{orderId}
        </if>
    </where>
</select>
```

### 4 `<set>`
- update 문에서 조건에 따라 변경할 대상이 다른 경우에 사용

```xml
<update id="updateOrderStatus" parameterType="map">
    /* OrdersMapperDao.updateOrderStatus */
    UPDATE ORDERS
    <set>
        <choose>
            <when test='orderStatus != null and orderStatus != ""'>
                SET ORDER_STATUS = #{orderStatus},
            </when>
            <otherwise>
                SET ORDER_STATUS = 'FAILED',
            </otherwise>
        </choose>
    </set>
</update>
```


### 4 `<trim>`
- 접두사, 접미사를 추가하거나 오버라이딩하여 커스텀하게 동적 쿼리를 만들 수 있습니다.
```text
- prefix : 실행될 쿼리의 가장 앞에 문자를 추가합니다.
- prefixOverrides : 실행될 쿼리의 가장 앞에 해당하는 문자가 있는 경우 지워줍니다.
- suffix : 실행될 쿼리의 가장 뒤에 문자를 추가합니다.
- suffixOverrides : 실행될 쿼리의 가장 뒤에 해당하는 문자가 있는 경우 지워줍니다.
```
```text
<trim prefix="문자열" prefixOverrides="문자열" suffix="문자열" suffixOverrides="문자열">
   실행될 쿼리
</trim>
```
```xml
<update id="updateOrderStatus" parameterType="map">
    /* OrdersMapperDao.updateOrderStatus */
    UPDATE ORDERS
    <trim prefix="SET" prefixOverrides="," suffixOverrides=",">
        <if test='orderStatus != null and orderStatus != ""'>
            ORDER_STATUS = #{orderStatus},
        </if>
        <if test='orderNo != null and orderNo != ""'>
            ORDER_NO = #{orderNo},
        </if>
    </trim>
    WHERE ORDER_ID = #{orderId}
</update>
```


### 4 `<foreach>`
- Collection, 배열 객체가 들어오는 경우 처리
```text
- item : 현재 반복되는 값을 저장할 변수
- index : 현재 반복되는 인덱스 값을 저장할 변수
- open : 쿼리가 실행될 때 앞에 추가될 접두사(시작문자열)
- close : 쿼리가 실행될 때 끝에 추가될 접미사(종료문자열)
- separator : 컬렉션이 반복될 때 추가될 문자(구분자 문자열)
```
```text
<foreach collection="컬렉션 변수" item="항목" index="인덱스" open="시작문자열" close="종료문자열" separator="구분자">
    #{item}
</foreach>
```

```xml
<select id="getStudentInfoList" parameterType="hashMap" resultType="hashMap">
    SELECT *
    FROM STUDENT
    WHERE STUDENT_ID IN
    <foreach collection="params" item="item" open="(" separator="," close=")">
        #{item.studentId}
    </foreach>
</select>
```