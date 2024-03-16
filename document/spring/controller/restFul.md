## RestFull 방식 API 
> REST(Representational State Transfer)는 web의 장점을 최대한 활용할 수 있는 아키텍처로 HTTP Method 별로 역할을 명시한다.

### REST API 디자인 가이드
* 1 URI는 정보의 자원을 표현해야 한다.
    + URI는 자원을 표현하는데 중점을 두어야 한다. 
    + `delete`, `select` 같은 행위에 대한 표현이 들어가면 안된다

* 2 행위는 Http method(Get, Post, Put/Patch, Delete) 등으로 표현한다. 

| HttpMethod | 사용목적                    |
|------------|-------------------------| 
| Get        | 특정 자원에 대한 조회 (리스트, 단건)  |
| Post       | 특정 자원에 대한 등록이나 추가 작업등   |
| Put       | 특정 자원에 대한 덮어쓰기 (파일 업로드) |
| Patch       | 특정 자원에 대한 일부 변경         |
| Delete       | 특정 자원에 대한 제거            |

### Get 사용법
- 사용목적: 특정 자원에 대한 조회 (리스트, 단건)
- PathVariable: 조회시 특정한 1건에 대한 정보 
- 예시1: 2024년 1월에 작업이 완료된 주문 리스트를 조회 
```java
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/orders")
public class OrdersApiControllerV1 {

    private final OrdersService ordersService;
    /**
     * url path: http(s)://{hostname}:{port}/v1/orders?orderStatus=Complete&startDate=2024-01-01&endDate=2024-12-31
     * http method: Get
     * path variable: None
     * request body: None
     */
    @GetMapping
    public List<OrdersFindResponse> findOrders(@ModelAttribute OrdersFindRequest request) {
        return ordersService.findOrders(request);
    }
}
```
- 예시2: 주문 번호가 1004인 주문의 상세 정보
```java
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/orders")
public class OrdersApiControllerV1 {

    private final OrdersService ordersService;
    /**
     * url path: http(s)://{hostname}:{port}/v1/orders/1004
     * http method: Get
     * path variable: 주문번호
     * request body: None
     */
    @GetMapping("/{orderNo}")
    public OrderFindResponse findOrder(@PathVariable String orderNo) {
        return ordersService.findOrder(orderNo);
    }
}
```
 
### Post 사용법
- 사용목적: 특정 자원에 대한 등록이나 추가 작업등
- 예시1: 새로운 주문을 수행한다.
```java
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/orders")
public class OrdersApiControllerV1 {

    private final OrdersService ordersService;
    /**
     * url path: http(s)://{hostname}:{port}/v1/orders
     * http method: Post
     * path variable: None
     * request body: 
     * [{"itemType":"BANANA", "stockCount":20}, {"itemType":"APPLE", "stockCount":10}]
     */
    @PostMapping
    public String saveItem(@RequestBody OrderCreateRequest request) {
        return ordersService.saveOrder(request);
    }
}
 
```

- 예시2: 주문 번호가 1004인 주문을 취소한다.
```java
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/orders")
public class OrdersApiControllerV1 {

    private final OrdersService ordersService;
    /**
     * url path: http(s)://{hostname}:{port}/v1/orders/1004/cancel
     * http method: Post
     * path variable: 주문번호
     * request body: 
     * {"message":"잘못된 상품으로 주문하여 취소합니다."}
     */
    @PostMapping("/{orderNo}/cancel")
    public String cancelItem(@PathVariable String orderNo, @RequestBody OrderCancelRequest request) {
        return ordersService.cancelOrder(orderNo, request);
    }
}
```

### Put, Patch, Delete
- 사용목적: 특정 자원에 대한 변경작업이 있는 경우 
- PathVariable: 변경할 특정한 1건에 대한 정보
- 예시1: 주문번호가 1004인 주문에 대한 전체 상품을 변경한다. (Put)
```java
/**
 * url path: http(s)://{hostname}:{port}/v1/orders/1004
 * http method: Put
 * path variable: 주문번호
 * request body: 
 * [{"itemType":"BANANA", "stockCount":10}, {"itemType":"APPLE", "stockCount":20}]
 */
```

- 예시2: 주문번호가 1004인 주문에 상품중에 `banana` 의 수량을 변경한다. (Patch)
```java
/**
 * url path: http(s)://{hostname}:{port}/v1/orders/1004
 * http method: Patch
 * path variable: 주문번호
 * request body: 
 * {"itemType":"BANANA", "stockCount":10}
 */
```

- 예시3: 주문번호가 1004인 주문에 상품중에 `banana` 를 제거한다. (Delete)
```java
/**
 * url path: http(s)://{hostname}:{port}/v1/orders/1004
 * http method: Delete
 * path variable: 주문번호
 * request body: None
 */
```

## 주의사항 
### 1. PathVariable 사용시 URL이 중복되는 경우가 발생할 수 있다. 
- 아래의 두개의 API 존재시에 주문번호가 `delete` 인 경우 2번쨰 API가 호출된다.(Spring은 항상 상세한것이 우선순위가 높다)
```text
@PostMapping("/api/v1/{orderId}")
@PostMapping("/api/v1/delete")
```
### 2. Put, Patch 구분 방법
> 특정 자원에 대한 완전한 덮어쓰기는 Put, 일부내용의 수정이면 patch를 사용하자.

### 3. 애매할 떄는 POST가 정답이다. 
> 예를 들어 CRUD 경계에 오묘한 작업이 필요한 경우 POST 로 하면 된다.   
>  url path: http(s)://{hostname}:{port}/v1/orders/1004/cancel