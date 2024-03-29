## SLF4J 와 구현기술
> SLF4J(Simple Logging Facade for Java)는 다양한 로깅 프레임 워크에 대한 추상화(인터페이스) 역할을 하는 라이브러리  
> 스프링 진형의 표준은 logback, 전자정보 프레임워크에서는 log4j2를 표준으로 한다.  

### Logback 과 Log4j2 의 차이점
> Log4j < LogBack < Log4j2 으로 점점 성능 차이 존재함
> LogBack에서 log4j보다 10배 정도 빠르게 수행, 서버 재기동 없이 변경 내용이 반영  
> Log4j2는 logback처럼 필터링 기능과 자동 리로딩을 지원하면서 Multi Thread 환경에서 비동기 로거의 경우 다른 로킹 프레임워크보다 많은 처리량과 짧은 대기 시간을 제공

### Logback 사용방법
> 스프링부트의 default logging 기능이라 기본적으로 수행됨
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration scan="true" scanPeriod="30 seconds">
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>[%d{MM.dd HH:mm:ss.SSS}] - [%-5level] - [%X{tran}] - [%logger{5}] - %msg%n</pattern>
        </encoder>
    </appender>

    <logger name="org.springframework" level="info"/>
    <logger name="kr.or.connect" level="info"/>

    <root level="info">
<!--        <appender-ref ref="FILE"/>-->
        <appender-ref ref="CONSOLE"/>
    </root>
</configuration>
```

### Log4j2 사용법 build.gradle
#### 우선 SpringBoot 기본으로 설정되어있는 slf4j log구현 클래스인 logback 라이브러리를 제외
```groovy
configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
	all*.exclude group: 'org.springframework.boot', module: 'spring-boot-starter-logging'
	all*.exclude group: 'org.springframework.boot', module: 'logback-classic'
}
```

#### Log4j2 의존성 추가 build.gradle
```groovy
// log4j2
implementation 'org.springframework.boot:spring-boot-starter-log4j2'
```


#### 참고 사이트
> [Logback 과 Log4j2 의 차이점](https://morethantoday.tistory.com/84?category=853479)


#### Logback 과 Log4j2 성능 비교

| sl4j 구현체| 총 가상사용자| TPS| 최고 TPS| 평균 테스트시간(ms)|
| --| --| --| --| --|
| Log4j2|  20개(Ramp-Up 사용)| 238.6| 634.0| 34.42|
| Logback|  20개20개(Ramp-Up 사용)| 229.6| 594.5| 39.66|
 


- Logback
![image](https://github.com/kim-taehan/egov-standard/assets/52950400/0d099c02-7f91-4082-8d21-2e5c5d1b1669)

- Log4j2
![image](https://github.com/kim-taehan/egov-standard/assets/52950400/4b01190d-0ffc-4419-b2b6-edb732eca75c)


