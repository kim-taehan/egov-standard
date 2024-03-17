## paging aop 
- Paging 관련해서는 2개의 aop가 존재합니다. Controller, Repository 에서 수행됩니다. 

### 1. Controller Aop
- 수행 조건: return type이 `ResEntity`이며, `@RequestBody` 객체가 Paging을 상속하고 있는 경우 
- before: 클라이언트에 올라온 데이터로 PageDto 객체를 만들어 RequestAttr에 입력한다.
- after: RequestAttr에서 PageDto를 받아 resEntity 객체에 세팅한다.

```java
@Aspect
@Slf4j
@RequiredArgsConstructor
public class PagingAspect {
    
    @Around("@annotation(postMapping) && (args(paging,..) || args(..,paging))")
    public Object doPagingByController(ProceedingJoinPoint joinPoint, PostMapping postMapping, Paging paging) throws Throwable {
        HttpServletRequest request = extractRequest();
        // 클라이언트에 올라온 데이터로 PageDto 객체를 만들어 RequestAttr에 입력한다.
        request.setAttribute("page", paging.getPage());
        ResEntity ret = (ResEntity) joinPoint.proceed();
        
        // RequestAttr에서 PageDto를 받아 resEntity 객체에 세팅한다.
        ret.updatePaging(paging.getPage());
        return ret;
    }
}
```


### 2. Repository Aop
- 수행 조건: `Repository` 메서드에서 `@PageQuery` 어노테이션을 가지고 있으며, Map 타입으로 param 이 넘어올때  
- before: RequestAttr에서 PageDto 를 추출하여 Map 타입으로 구현된 Param에 입력한다.
- after:조회 쿼리 아이디 + `Count` 로 구현된 쿼리에서 카운트 쿼리 수행후 그 결과를 PageDto에 입력한다

```java
public class PagingAspect {

    public static final String SUFFIX_ID = "Count";
    private final PagingMapper pagingMapper;

    @Around("@annotation(pageQuery)")
    public Object doPageQuery(ProceedingJoinPoint joinPoint, PageQuery pageQuery) throws Throwable {

        HttpServletRequest request = extractRequest();
        PageDto page = (PageDto) request.getAttribute("page");

        updatePageArgs(joinPoint, page);

        // paging query execute
        Object result = joinPoint.proceed();

        // find count sql query
        String queryId = pageQuery.value() + SUFFIX_ID;

        // count query execute
        int totalCount = findTotalCount(queryId, joinPoint.getArgs());

        page.updateTotal(totalCount);
        return result;
    }
}
```