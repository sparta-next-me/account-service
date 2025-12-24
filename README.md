# Financial Data Integration API (Backend)
> **사용자가 여러 은행의 자산을 한곳에서 관리하고, 개인화된 금융 상품 추천(RAG)을 받을 수 있도록 돕는 서비스입니다. <br>
> **전체 서비스 중 계좌 연동, 소비 데이터 수집, 금융 상품 RAG 파이프라인 구축 담당** > 외부 금융 API(CODEF, 금감원)를 활용하여 흩어진 자산 데이터를 통합하고, 분석 가능한 데이터로 가공하는 핵심 엔진을 개발했습니다.


## 🛠 Tech Stack
- **Language**: Java 21 / Spring Boot 3.x
- **Database**: Postgre / JPA (Hibernate)
- **Communication**: Spring Cloud OpenFeign
- **External API**: 
  - **CODEF API**: 시중 은행(신한, 국민, 기업) 계좌 및 거래내역 연동
  - **금융감독원 API**: 예/적금 상품 정보 수집
- **Tools**: Swagger (API Documentation), Postman

---

## Key Implementation (담당 기능)

### 1. 계좌 및 자산 통합 (Account Management)
* **다중 기관 연동**: CODEF API를 통해 시중 3대 은행(신한, 국민, 기업)의 계좌 연동, 조회, 해지 로직 구현
* **자산 데이터 가공**: 백엔드에서 연동된 모든 계좌의 잔액을 합산하여 **'총 자산금'**을 산출하는 비즈니스 로직 개발
* **API 추상화**: 향후 은행 추가 시 유연하게 대응할 수 있도록 외부 API 호출부와 비즈니스 로직 분리

### 2. 소비 내역 분석 (Transaction Tracking)
* **지출 리포트 기반 데이터**: 실시간 거래 내역 생성 및 조회 API 구현
* **고도화 설계**: 대량의 거래 데이터를 효율적으로 관리하기 위해 **Daily Scheduling(Spring Batch)** 도입을 고려한 DB 스키마 설계

### 3. 금융 상품 파이프라인 (Product )
* **금감원 데이터 수집**: 예/적금 정보를 동기화하여 서비스 내 추천 데이터셋 구축
* **RAG 서버 데이터 공급**: LLM 기반 유사도 분석 서버가 데이터를 원활히 참조할 수 있도록 **OpenFeign**을 활용한 전용 API 및 전송 파이프라인 구축

---

## 🏗 System Architecture


---

## 📂 Project Structure (My Module)
```text
src/main/java/com/project/finance/
├── account        # 계좌 연동 로직 (Connect/Delete/Sync)
├── transaction    # 거래 내역 수집 및 지출 분류
├── product        # 금융감독원 API 연동 및 상품 정보 관리
├── feign          # RAG 서버 및 외부 API 통신 인터페이스 (OpenFeign)
└── common         # 공통 예외 처리 및 유틸리티
```

Database ERD (Major Entities)
Account: 거래 내역과의 1:N 관계를 통해 확장성 확보

Transaction: 일일 스케줄링 및 인덱싱을 고려한 설계

Product: RAG 분석을 위한 메타데이터 필드 포함

💡 Technical Decision & Trouble Shooting
✅ 왜 OpenFeign을 사용했는가?
단순 RestTemplate보다 선언적인(Interface-based) 코드를 작성할 수 있어 외부 API 명세가 바뀌더라도 비즈니스 로직의 수정을 최소화할 수 있다고 판단했습니다. 특히 RAG 서버와의 통신 시 가독성과 유지보수성을 크게 향상시켰습니다.

✅ 데이터 정규화 문제 (External API Integration)
문제: CODEF API 응답 데이터가 은행마다 필드명이나 날짜 포맷이 미세하게 다른 이슈 발생

해결: DataMapper 클래스를 별도로 두어 각 은행별 응답을 서비스 내부의 공통 TransactionVO로 변환하는 전략 패턴(Strategy Pattern) 구조 적용

✅ 향후 고도화 계획 (Scalability)
Spring Batch: 현재 실시간 호출 방식에서 매일 새벽 트래픽이 적은 시간에 데이터를 동기화하는 배치 프로세스로 고도화 예정

Query Optimization: 거래 내역이 누적됨에 따라 userId와 transactionDate에 복합 인덱스를 적용하여 조회 성능 최적화 예정

🔗 Main API Endpoints
Category	Method	Endpoint	Description
Account	POST	/api/v1/accounts	신규 은행 계좌 연동 (CODEF)
Account	DELETE	/api/v1/accounts/{id}	계좌 연동 해지
Transaction	GET	/api/v1/transactions	지출 내역 및 거래 데이터 조회
Product	POST	/api/v1/products/sync	금감원 상품 정보 최신화


