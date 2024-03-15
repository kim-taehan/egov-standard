## logging 사용방법 (개발자 교육)
> Lombok 과 연계하여 사용하는 방식으로 실제 로직에서 사용할 수 있다.   
 
#### 클래스 상단에 @Slf4j 어노테이션을 정의하고, 로그 출력시 log.{로그레벨}(String message) 형태로 사용하자
```java
@Slf4j
@DisplayName("[기능] Logging Test")
public class loggingTest {
    @Test
    @DisplayName("여러 내용은 log를 출력한다.")
    void loggingTest(){
        log.info("log.getClass = {}", log.getClass());
        log.trace("logging call=trace");
        log.debug("logging call=debug");
        log.warn("logging call=warn");
        log.info("logging call=info");

        RuntimeException re = new RuntimeException("신규 애러");
        log.error("logging call=error", re);

        assertThat(true).isTrue();
    }
}
```

### 로그 레벨 
> trace > debug > info > warn > error 순서로 단계가 높아진다    
> 일반적으로 로컬, 개발 서버에 로그 설정은 debug 부터 운영서버는 info 부터로 정의한다.
> 


### 유의 사항 
####  log 를 남길때 `+` 를 사용하는 방식의 경우 출력 대상이 아닌 경우에도 연산이 일어난다. 
```text
log.debug("log.getClass " + log.getClass()); (X)  
log.debug("log.getClass = {}", log.getClass()); (O)  
```

