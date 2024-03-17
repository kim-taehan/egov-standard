## Transaction 설명 
- `SkAbstractService` 를 상속받으면 기본적으로 `@Transactional(readOnly = true)` 로 설정  
- write 권한이 필요한 경우 ServiceImpl 의 메서드에 `@Transactional`을 입력한다.
- read-only 의 경우 Rollback에 대한 비용이 없기 때문에 write transaction 보다 비용이 감소

```java
public class OrdersServiceImpl extends SkAbstractService implements OrdersService {

    // 단순 조회 서비스의 경우
    @Override
    public OrderFindResponse findOrder(String orderNo) {
        // 생략..
    }

    // write connection 이 필요한 경우 
    @Override
    @Transactional
    public String saveOrder(OrderCreateRequest request) {
        // 생략..
    }
}
```

### 주의 사항 
> `@Transactional` 의 경우 proxy 방식의 aop 를 통해 트랜잭션이 주입되기 때문에 
> 같은 클래스에서 호출되는 경우 Transaction이 생각과 다른게 연결될 수 있다. 