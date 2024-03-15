## Dependency Injection (의존성 주입)
> Spring에서는 기본적은 bean을 등록하여 싱글톤 방식으로 동작하게 된다.   
> 의존성 주입은 외부에 의존성을 주입해서 실제 사용시에 구체화 클래스를 의존하지 않고도 사용할 수 있게 한다.   
> 의존성 주입방법에는 여러가지 있지만 그중에서 생성자 주입, 필드 주입에 방법에 대해서만 나열한다. 


### 필드 주입
> Spring, java 개발자들이 지양하는 방법이다. (심지어 특정 Tool에서는 이방식을 사용시에 경고메시지를 보여준다.)  
> 전자정보 프레임워크에 권장하는 방식   
> @Autowired, @Resource 어노테이션을 사용하여 필드에 직접 주입하는 방식 

```java
class ItemService {
    
    @Autowired 
    private ItemRepository itemRepository;
    
    @Resource("OrderService") // 추상화가 있는 경우 구현체를 직접입력 X 
    private OrderService orderService;
}
```

### 생성자 주입 
> Spring 진형 및 많은 개발자들이 권장하는 방식.  
> Lombok과 연계하여 필드주입보다 더 쉽게 사용할 수 있다. (@RequiredArgsConstructor)  
> @RequiredArgsConstructor : final 전역변수들의 생성자를 자동으로 만들어준다.

- Lombok 사용 버전
```java 
@RequiredArgsConstructor  
class ItemService {
   
    private final ItemRepository itemRepository;
    private final OrderService orderService;
}
```

- Lombok 미사용 버전
```java 
class ItemService {
   
    private final ItemRepository itemRepository;
    private final OrderService orderService;

    @Autowired // 다른 생성자가 없는 경우 생략 가능
    public ItemService(ItemRepository itemRepository, OrderService orderService){
        this.itemRepository = itemRepository;
        this.orderService = orderService;
    }
    
}
```

### 생성자 주입을 사용해야 되는 이유 

#### 1. 불변 객체로 생성할 수 있다. (final)
> 런타임 시점에 객체를 임의로 변경할 수 없다.  
> 이는 불변성을 해칠 수 있으므로 객체의 안정성을 보장하기 어렵습니다.

#### 2. 순환참조 문제가 발생한다.
> 컴파일 시점에 순환참조에 대한 코드를 발견할 수 없고, 런타임 시점에 오류가 발생하게 된다.

#### 3. 테스트 하기가 어렵다. 
> 필드 주입을 사용하면 의존성 주입을 위해 Spring Container가 필요하게 되므로 테스트가 어렵습니다.  
> 객체를 직접 생성하면서 의존성을 주입하는 생성자 주입을 사용하면, 테스트가 더 용이하게 됩니다.




