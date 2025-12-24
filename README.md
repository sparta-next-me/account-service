# 자산, 금융상품 관리 API (Backend)
> 사용자가 여러 은행의 자산을 한곳에서 관리하고, 개인화된 금융 상품 추천(RAG)을 받을 수 있도록 돕는 서비스입니다. <br>
> **전체 서비스 중 계좌 연동, 소비 데이터 수집, 금융 상품 RAG 파이프라인 구축 담당** > 외부 금융 API(CODEF, 금감원)를 활용하여 흩어진 자산 데이터를 통합하고, 분석 가능한 데이터로 가공하는 핵심 엔진을 개발했습니다.

####  목차
1. [기술 스택](#기술-스택)
2. [담당 기능](#담당-기능)
     - [계좌 및 자산 통합](#1-계좌-및-자산-통합)
     - [소비 내역 분석](#2-소비-내역-분석)
     - [금융 상품 파이프라인](#3-금융-상품-파이프라인)
3. [ERD](#erd)
4. [Technical Decision & Trouble Shooting](#technical-decision--trouble-shooting)
5. [API Endpoints](#api-endpoints)


## 기술 스택
- **Language**: Java 21 / Spring Boot 3.x
- **Database**: Postgre / JPA (Hibernate)
- **Communication**: Spring Cloud OpenFeign
- **External API**: 
  - **CODEF API**: 시중 은행(신한, 국민, 기업) 계좌 및 거래내역 연동
  - **금융감독원 API**: 예/적금 상품 정보 수집
- **Tools**: Swagger (API Documentation), Postman

---

## 담당 기능

### 1. 계좌 및 자산 통합 
* **다중 기관 연동**: CODEF API를 통해 시중 3대 은행(신한, 국민, 기업)의 계좌 연동, 조회, 해지 로직 구현
* **자산 데이터 가공**: 백엔드에서 연동된 모든 계좌의 잔액을 합산하여 **'총 자산금'**을 산출하는 비즈니스 로직 개발
* **API 추상화**: 향후 은행 추가 시 유연하게 대응할 수 있도록 외부 API 호출부와 비즈니스 로직 분리

### 2. 소비 내역 분석
* **On-Demand 수집**: 사용자가 요청한 특정 날짜 범위의 거래 내역을 실시간으로 가져와 DB에 동기화
* **거래내역 데이터 수집**  : 거래내역 정보를 임베딩하여 AI 분석을 위한 데이터셋 구축

### 3. 금융 상품 파이프라인
* **금감원 데이터 수집**: 예/적금 정보를 동기화하여 서비스 내 추천 데이터셋 구축
---

# ERD
## <img width="828" height="538" alt="스크린샷 2025-12-24 오후 3 43 01" src="https://github.com/user-attachments/assets/aa7e1b40-631d-4041-afad-5e4961f48319" />
---


# Technical Decision & Trouble Shooting
### [Technical Decision]
#### RestClient & OpenFeign
1. OpenFeign (RAG 서버 통신)

 - 비즈니스 로직과 통신 로직을 분리하여 가독성을 높임

 - 유지보수성: 내부 통신 시, 인터페이스만으로 호출이 가능해 코드의 응집도를 향상시킴

2. RestClient (외부 금융 API 연동)

 - CODEF 및 금감원 API와 같이 기관별로 상이한 에러 응답 처리와 동적인 헤더 설정이 필요한 경우, 직접적인 제어가 가능한 RestClient를 채택
#### 고도화 계획
현재 혼용 중인 통신 방식을 향후 Spring Cloud OpenFeign으로 통일할 에정

### [Trouble Shooting] 
#### 기관별 상이한 은행 코드 통합 문제
**문제** : CODEF API의 은행 코드와 금융감독원 API의 은행 코드가 서로 달라, 특정 은행의 계좌와 해당 은행의 금융 상품을 매칭하기 어려움 발생

**해결**: 내부적으로 Bank_Code 매핑 테이블을 구축. 각 기관의 코드를 서비스 표준 코드로 변환하는 로직을 구현하여, 사용자가 연동한 은행의 상품을 정확히 식별할 수 있도록 데이터 정규화 수행

---

## API Endpoints
### 계좌
| Category | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| **Account** | POST | `/v1/account/create ` | 신규 은행 계좌 연동 (CODEF) |
| **Account** | GET  | `/v1/account/user-account ` | 본인 계좌 전체조회 |
| **Account** | POST | `/v1/account/condition` | 본인 계좌 단건 조회 |
| **Account** | DELETE | `/v1/account` | 계좌 연동 해지 |

### 거래내역
| Category | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| **Transaction** | POST | `/v1/account/tran` | 거래내역 연동 (CODEF) |
| **Transaction** | GET | `/v1/account/tran/all` | 본인 거래내역 전체조회 |
| **Transaction** | POST | `/v1/account/tran/condition` | 지출 내역 및 거래 데이터 조회 |
| **Transaction** | GET | `/v1/account/tran//tranList/{userId} ` | 백터화 시키키 위한 거래내역 정보 |

### 은행코드
| Category | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| **BankCode** | POST | `/v1/account/bank` | 은행코드 생성 |

### 금융상품
| Category | Method | Endpoint | Description |
| :--- | :--- | :--- | :--- |
| **BankItem** | POST | `/v1/account/financial-products/deposit   ` | 적금 상품 연동(금융감독원) |
| **BankItem** | POST | `/v1/account/financial-products/saving ` | 예금 상품 연동(금융감독원)|
| **BankItem** | GET | `/v1/account/financial-products  ` | 금융상품 전체조회  |
| **BankItem** | GET | `/v1/account/financial-products/{bankCode}/{bankItemId}` | 해당 은행의 금융상품 단건 조회 |
| **BankItem** | GET | `/v1/account/financial-products/report ` | 벡터화를 위한 금융 상품 데이터 추출 |

