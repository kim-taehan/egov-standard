# 전자정부 프레임워크 호환성 가이드
- [전자정보 프레임워크_호환성확인 가이드라인.pdf](%EC%A0%84%EC%9E%90%EC%A0%95%EB%B3%B4%20%ED%94%84%EB%A0%88%EC%9E%84%EC%9B%8C%ED%81%AC_%ED%98%B8%ED%99%98%EC%84%B1%ED%99%95%EC%9D%B8%20%EA%B0%80%EC%9D%B4%EB%93%9C%EB%9D%BC%EC%9D%B8.pdf)
- [표준프레임워크 세부 적용기준.pdf](%5B%EC%B0%B8%EC%A1%B0%5D%ED%91%9C%EC%A4%80%ED%94%84%EB%A0%88%EC%9E%84%EC%9B%8C%ED%81%AC%20%EC%84%B8%EB%B6%80%20%EC%A0%81%EC%9A%A9%EA%B8%B0%EC%A4%80.pdf)

## 1 실행환경 변경 금지
> 전자정부 실행환경 라이브러리는 변경할 수 없습니다.
- 프로젝트 폴더 내에 존재하는 자바 바이너리 파일 중 org.egovframe.rte로 시작하는 파일을 대상으로 합니다
- 프로젝트 내 전자정부 실행환경의 SHA1 해시코드는 원본과 동일해야 합니다.
- 프로젝트 내 전자정부 실행환경의 용량은 원본과 동일해야.
- 존재하지 않는 실행환경 라이브러리에 대해서는 검사를 하지 않습니다.
- 호환성 확인 기반 S/W 최소 Maven Dependency
```text
org.egovframe.rte.ptl.mvc-버전.jar 
org.egovframe.rte.fdl.cmmn-버전.jar 
org.egovframe.rte.psl.dataaccess-버전.jar 
org.egovframe.rte.fdl.logging-버전.jar
```

## 2. 설정파일 위치
> 설정파일들은 특정 위치에 존재해야 합니다.
- 프로젝트 폴더 내에 존재하는 xml 파일 중 beans 혹은 sqlMap 엘레먼트를 가지고 있는 파일로 대상으로 합니다.
- 설정파일은 프로젝트 루트에 위치할 수 없습니다.
- 설정파일들은 공통적인 상위 디렉토리를 가져야 합니다. 
- WEB-INF 및 target 폴더 내에 있는 xml 파일들은 대상에서 제외
- 설정파일들은 프로젝트 루트에 있지 않으며 프로젝트 루트의 특정 하위 디렉토리를 공통 상위 디렉토리로 가져야 한다.

## 3. 데이터 액세스 아키텍처 규칙 
> DAO 클래스들은 전자정부 표준 아키텍처를 준수하여야 합니다.
- 점검 대상 :
```text
- SQL매핑 파일에 정의된 쿼리 아이디를 매개 변수로 함수를 호출한다.
⁻ @Repository 어노테이션을 가지고 있다.
⁻ EgovAbstractDAO를 상속받았다.
⁻ SqlMapClientDaoSupport를 상속받았다.
```
- Ibatis SqlMapClientDaoSupport, Mybatis SqlSessionDaoSupport에서 제공하는 insert, delete, update, select, list, …등 메소드를 호출할 수 없습니다
```text
- 데이터 액세스 규칙은 모든 DAO 클래스들이 EgovAbstractDAO 또는 EgovAbstractMapper를 상속받아서 사용하여야 하며, EgovAbstractDAO또는EgovAbstractMapper를무시하고사용하지않는경우를방지하기위한규칙입니다.
- EgovAbstractDAO 또는 EgovAbstractMapper의 활용이 프로젝트에 부적합한 경우 해당 클래스를 확장한 클래스를
상속받아서 활용하여도 무방합니다.
- Mybatis Mapper Interface 방식 사용 시 표준프레임워크에서 제공하는 MapperConfigurer을 사용해야 합니다
```

## 4. MVC 아키텍처 규칙
> Controller 클래스들은 전자정부 표준 아키텍처를 준수하여야 합니
- 점검대상 : 프로젝트 내의 자바클래스 중 다음조건을 하나라도 만족하면 점검대상이 됩니다.
```text
 @Controller 어노테이션을 가지고 있다.
⁻ @RequestMapping 어노테이션을 사용하였다.
⁻ @RequestParam 어노테이션을 사용하였다.
⁻ @ModelAttribute 어노테이션을 사용하였다.
⁻ @SessionAttribute 어노테이션을 사용하였다
```
- Ibatis SqlMapClientDaoSupport, Mybatis SqlSessionDaoSupport 클래스의 메소드를 호출할 수 없습니다.
- HibernateDaoSupport 클래스의 메소드를 호출할 수 없습니다.
- DAO 클래스의 메소드를 호출할 수 없습니다.
```text
MVC 아키텍처 규칙은 Controller 클래스가 DAO 클래스를 직접 호출하는 것을 막기 위
한 규칙입니다. 특히 코드 서비스등을 활용할 때 주의하시기 바라겠습니다. 여기서 DAO
클래스의 정의는 데이터 아키텍처 점검 대상 클래스들에 HibernateDaoSupport를 상속
받은 클래스들을 포함합니다
```

## 5 서비스 아키텍처
- 점검대상 : 프로젝트 내의 자바클래스 중 다음조건을 하나라도 만족하면 점검대상이 됩니다.
```text
- @Service 어노테이션을 가지고 있다.
⁻ EgovAbstractServiceImpl을 상속받았다.
```
- EgovAbstractServiceImpl을 확장해야 합니다.
- 특정 인터페이스를 구현하여야 합니다.
```text
서비스 아키텍처 규칙은 서비스 클래스로 사용되는 클래스들이 전자정부 표준프레임워
크 실행환경의 EgovAbstractServiceImpl을 확장하여야 한다는 규칙과, 클래스간 결합도
를 낮추기 위하여 서비스 클래스들은 특정한 인터페이스를 선언하고 해당 인터페이스를
확장하여야 한다는 규칙으로 이루어져 있습니다. EgovAbstractServiceImpl의 활용이 프
로젝트에 부적합한 경우 해당 클래스를 상속받은 공통 추상 서비스 클래스를 작성하여
해당 클래스를 상속받는 형태로 활용하여 주시기 바라겠습
```

## 6 실행환경 확장 규칙
> 전자정부 표준프레임워크 실행환경은 규정에 맞게 확장되어야 합니다.
- 프로젝트 내의 자바 클래스 중 org.egovframe.rte 패키지에 속한 클래스를 상속받은 클래스는 모두 대상이 됩니다.
- 해당 클래스는 org.egovframe.rte 패키지 내에 정의될 수 없습니다.
- 해당 클래스는 Egov라는 이름으로 시작할 수 없습니다.
```text
실행환경 확장규칙은 실행환경 라이브러리의 클래스들을 확장하여 독자적으로 만들어
낸 클래스들이 전자정부 표준프레임워크와 구분되도록 하기 위하여 만들어진 규칙입니
다. 특정한 이유로 실행환경 클래스를 확장하였을 경우, 해당 클래스의 패키지와 이름의
작성에 주의하여 주시기 바라겠습니
```

## 7 표준프레임워크 활용 규칙
> 전자정부 표준프레임워크 실행환경은 적극적으로 활용되어야 합니다
- 점검대상 : 점검대상이 되는 전체 프로젝트가 대상이 됩니다. 
- 해당 프로젝트 내에는 적어도 한 개 이상의 실행환경 라이브러리가 존재해야 합니다. 
- 해당 프로젝트 내에는 적어도 한 개 이상의 DAO 클래스가 있어야 합니다. 
- 해당 프로젝트 내에는 적어도 한 개 이상의 Service 클래스가 있어야 합니다.
```text
표준 프레임워크 활용 규칙은 표준 프레임워크를 실제로 활용하는지를 검사하기 위한
규칙으로, 실행환경 라이브러리의 존재여부 및 아키텍처 정의를 따르는지 여부를 확인
합니다. 데이터 엑세스 아키텍처 규칙 및 서비스 아키텍처 규칙을 준수하지 않는 경우
DAO 클래스 및 Service 클래스가 검출되지 않아서, 이 규칙이 더불어서 위반으로 표시
될 수 있습니다. 먼저 해당 규칙들을 준수하는 
```