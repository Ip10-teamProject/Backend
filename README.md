# Backend

## Summary
> - 애플리케이션 이름: Backend
> - Public IP 주소: 3.36.51.67
> - 포트 번호: 8080
> - 개발 환경: IntelliJ IDEA
> - API 테스트 도구: Postman
> - 빌드 도구: Gradle
> - 협업 도구: GitHub
> - 배포 환경: AWS
> - 자바 버전: 17
> - 기술 스택: Spring Boot 3.2.2, Spring Data JPA, Spring Security 6, Redis

<br/>

## Infrastructure Architecture
### - 
> <div><img src="https://velog.velcdn.com/images/dmitry__777/post/3f5aca01-2624-4fe6-9a70-80696581ab8c/image.png" align="center" width="70%"></img></div>

<br/>

## API Document


### - RESTful API
> - 리소스 중심 설계: 리소스는 고유한 URI로 식별되며, HTTP Method와 end-point만 확인해도 어떤 기능을 위한 API인지 직관적으로 인지 가능

### - Swagger UI
> - API 문서 생성 자동화 툴
> - 웹 애플리케이션 실행 후 아래의 link에 접속하여 end-point, 요청 파라미터 등에 대한 정보를 확인
> - End-point: /swagger-ui/swagger-ui/index.html
> - link: http://3.36.51.67:8080/swagger-ui/swagger-ui/index.html

<br/>

## DBMS
### - PostgreSQL
> - 개발 단계: Local DB
> - 배포 단계: AWS RDS

### - ER Diagram
> ![](10조erd.png)

<br/>

## Authentication & Authorization
### - Spring Security 6
> - SecurityFilterChain에서 인증 및 인가를 처리
> - UsernamePasswordAuthenticationFilter의 앞에 JwtRequestFilter 커스텀 필터를 추가
> - 클라이언트에서 로그인 요청 시 JwtRequestFilter를 통과하여 CustomUserDetails 객체를 추가
### - JWT
> - 서버 측에서는 Authorization 요청 헤더에 있는 access token 값에서 있는 유저의 정보를 추출
> - CustomUserDetails 객체와 access token에서 추출한 유저의 정보를 비교하여 인증 및 인가를 처리

<br/>

## Deployment
### - AWS
> - Root 계정에서 EC2 및 RDS instance를 생성
### - FTP
> - FileZilla를 사용하여 .jar 파일을 업로드
### - SSH
> - EC2 instance에 로그인하여 .jar 파일을 실행
