# Vendi_Verse (예약구매) 프로젝트

## 프로젝트 소개

---
MSA 아키텍쳐를 적용한 Springboot ecommerce 프로젝트입니다.
주제에 맞게 각 서비스(사용자, 상품, 주문)를 독립적으로 개발, 배포, 스케일링할 수 있습니다.

## 목차

[//]: # (1. [프로젝트 개요]&#40;#프로젝트-개요&#41;)
1. [기술 스택](#기술-스택)
2. [아키텍처](#Service-Architecture)
3. [ERD](#ERD)
4. [API 문서](#API-문서)
5. [성능 개선 및 트러블 슈팅](#트러블-슈팅)

   [//]: # (6. [성능 개선 및 트러블 슈팅]&#40;#성능-개선-및-트러블-슈팅&#41;)
   
[//]: # (## 프로젝트 목적)

[//]: # ()
[//]: # (1. Micro Service Architecture&#40;MSA&#41;로의 전환:)

[//]: # (  - 서비스 간 독립성 증가:)

[//]: # ()
[//]: # (2. 재고 관련 동시성 문제 해결을 위한 Redisson 도입:)

[//]: # (  - 분산 환경에서의 동시성 제어: Redisson의 분산 락을 사용하여 여러 서버에서 동시에 접근하는 재고 문제를 해결했습니다.)

[//]: # ()

## 기술 스택

- **Backend**: Java 21, Spring Boot 3, Spring Cloud, Spring Data JPA
- **Database**: MySQL, Redis
- **API Gateway**: Spring Cloud Gateway
- **Service Discovery**: Netflix Eureka
- **Containerization**: Docker

[//]: # (- **CI/CD**: GitHub Actions)

[//]: # (- **Message Broker**: RabbitMQ)

## Service Architecture

![Architecture Diagram](https://github.com/user-attachments/assets/ea6c4f8f-36b2-4eda-ac75-04890ef42808)

## ERD

![pre-order](https://github.com/user-attachments/assets/c5e21d67-e2ac-4490-8c4f-4f4bc412707a)


## API 문서

### [VendiVerse API (Postman)](https://documenter.getpostman.com/view/35281421/2sA3kSoiX7#3bdc97e7-6053-4442-8127-6125d994dee3)

[//]: # (## 성능 개선 및 트러블 슈팅)
[//]: # (## 트러블 슈팅)

[//]: # ()
[//]: # (### 성능 최적화 사례)

[//]: # ()
[//]: # (- Query 성능 개선?)

[//]: # ()
[//]: # (-> 기존 로직 수치 vs 개선 로직 수치)


[//]: # (- Redis Cache 도입)

[//]: # ()
[//]: # (-> LocalCache vs Redis Cache 특징 블로그에 게시)

[//]: # ()
[//]: # (-> Redis에 어떤 방식으로 Cache 저장을 했는지 기록)

[//]: # ()
[//]: # (-> 반복적인 요청에 대해 응답 쿼리 수를 얼마나 줄일 수 있었는지 기록)

[//]: # ()
[//]: # (예&#41; 사례: 캐시를 도입하여 데이터베이스 쿼리 수를 1000건에서 200건으로 줄임.)

[//]: # ()
[//]: # (  수치: 요청당 처리 시간 및 CPU 사용량 감소율.)

### 트러블 슈팅

[//]: # (- JWT vs Session)
- [API Gateway 도입을 통한 서비스 요청 관리 및 JWT 검증 구현](https://challduck.tistory.com/29)
- [재고 감소 동시성 이슈 해결](https://challduck.tistory.com/30)

[//]: # (- 예약구매 상품 오픈 시간 이전 구매 이슈)
