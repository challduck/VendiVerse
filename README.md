# Vendi_Verse (예약구매) 프로젝트

## 프로젝트 소개

---
VendiVerse는 ~

## 목차

1. [프로젝트 개요](#프로젝트-개요)
2. [기술 스택](#기술-스택)
3. [아키텍처](#아키텍처)
4. [서비스 설명](#서비스-설명)
5. [API 문서](#API-문서)

## 프로젝트 목적

VendiVerse는 Monolithic Architecture 에서 Micro Service Architecture로 전환하며, 



## 🔧 기술 스택

- **Backend**: Spring Boot, Spring Cloud, Spring Data JPA
- **Database**: MySQL, Redis
- **Message Broker**: RabbitMQ
- **API Gateway**: Spring Cloud Gateway
- **Service Discovery**: Netflix Eureka
- **Containerization**: Docker
- **CI/CD**: GitHub Actions

## 아키텍처

아키텍처 이미지 첨부 예정

## ERD

![pre-order](https://github.com/user-attachments/assets/c5e21d67-e2ac-4490-8c4f-4f4bc412707a)


## 서비스 설명

### User Service
- 사용자 관리 (회원가입, 로그인 등)

### Product Service
- 상품 관리 (상품 등록, 조회, 수정, 삭제)

### Order Service
- 주문 관리 (주문 생성, 조회)

### Inventory Service
- 재고 관리 (재고 확인, 업데이트)

### Payment Service
- 결제 처리

## API 문서

### [VendiVerse API (Postman)](https://documenter.getpostman.com/view/35281421/2sA3kSoiX7#3bdc97e7-6053-4442-8127-6125d994dee3)

## ⚒️ 프로젝트에서 해결하고자 했던 문제

### 성능 최적화 사례

- Query 성능 개선

### 트러블 슈팅

- **재고 감소 동시성 이슈**

- **예약구매 상품 옾느 시간 이전 구매 이슈**
