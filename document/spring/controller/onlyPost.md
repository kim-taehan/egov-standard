## Controller 작성법
> 사실 누가 언제부터 시작된 내용인지는 기억이 나지 않지만 Get은 보안에 위험하고 Post는 안전하니깐 Post를 사용해야 된다는 SI 전설이 있다.   
> SSR 방식(jsp, php) 의 경우 URL에 정보가 보이니깐 그럴 수 있다고 생각하지만, CSR 방식은????  
> SI 관례라고 편하게 생각하자 

```java
@RequiredArgsConstructor
@RestController
@RequestMapping("/v2/orders")
public class OrdersApiControllerV2 {

    private final OrdersService ordersService;

    @PostMapping("/findOrders")
    public ResEntity<List<OrdersFindResponse>> findOrders(@Valid @RequestBody OrdersFindRequest request) {
        List<OrdersFindResponse> orders = ordersService.findOrders(request);
        return ResEntity.ok(orders);
    }

    @PostMapping("/saveOrder")
    public ResEntity<String> saveOrder(@RequestBody OrderCreateRequest request) {
        String ret = ordersService.saveOrder(request);
        return ResEntity.ok(ret);
    }

    @PostMapping("/cancelOrder")
    public ResEntity<String> cancelOrder(@RequestBody OrderCancelRequest request) {
        String ret = ordersService.cancelOrder(request.getOrderNo(), request);
        return ResEntity.ok(ret);
    }
}
```

