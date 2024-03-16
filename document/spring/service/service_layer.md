## Service Layer 설명 
> 비지니스에 대한 로직이 존재하는 부분입니다.

### 전자정보 프레임워크 적용기준
- 특정 인터페이스를 구현하여야 합니다. (품질 검증 대상)
- EgovAbstractServiceImpl을 확장해야 합니다. (품질 검증 대상)

### 작성방법 
#### 1. 외부에 노출할 Interface를 생성한다. (외부 노출이 필요한 함수만 정의)
```java
public interface OrdersService {
    List<OrdersFindResponse> findOrders(OrdersFindRequest request);
    OrderFindResponse findOrder(String orderNo);
    String saveOrder(OrderCreateRequest request);
    String cancelOrder(String orderNo, OrderCancelRequest request);
}
```

#### 2. Service Interface 가 존재하는 위치에 `impl` 디렉토리를 만들고 구현체 클래스를 작성
> class name은 일반적으로 service명 + `Impl` (1개의 구현체가 존재하는 경우)  
> 여러 구현체가 존재하는 경우에는 class 명명규칙에 따라 정의하자

![service_impl_path.png](service_impl_path.png)


#### 3. SkAbstractService 확장 (이름은 사이트따라 변경됨)
> SkAbstractService 는 전자정보 프레임워크에 EgovAbstractServiceImpl을 확장한 클래스이다.

```java
@Transactional(readOnly = true)
public abstract class SkAbstractService extends EgovAbstractServiceImpl {
    // 생략 ... 
}
```

#### 4. 실제 비지니스 서비스 클래스 
> `extends SkAbstractService` + `implements {업무에서 정의한 Interface}`  
> interface 의 경우 비지니스에서 필요한 경우 여러 인터페이스를 상속받아도 된다.  

```java
@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class OrdersServiceImpl extends SkAbstractService implements OrdersService {
    // 생략 ... 
}
```
