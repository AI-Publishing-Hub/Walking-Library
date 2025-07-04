# KT 걷다가서재 (Walking-Library)

> [cite\_start]KT AIVLE School 미니프로젝트 최종 수행 보고서[cite: 1, 2].
> [cite\_start]**팀:** 9반 24조 [예스24] [cite: 3]

## 📝 프로젝트 개요 (Overview)

[cite\_start]\*\*'걷다가서재'\*\*는 MSA(Microservice Architecture) 기반의 웹소설 출판 플랫폼입니다[cite: 1]. [cite\_start]작가가 소설을 집필하면, LLM AI가 자동으로 책 표지와 줄거리를 생성하여 출판을 돕는 서비스를 제공합니다[cite: 20]. [cite\_start]독자들은 포인트를 충전하여 작품을 개별 구매하거나 월 구독 서비스를 통해 무제한으로 열람할 수 있습니다[cite: 22].

## ✨ 주요 기능 (Features)

### ✒️ 작가 관점

  * [cite\_start]작가로 등록 신청하고 승인받은 후 작품을 출판할 수 있습니다[cite: 18].
  * [cite\_start]웹 에디터를 통해 글을 작성하고 임시 저장하거나 최종 제출할 수 있습니다[cite: 19].
  * [cite\_start]원고를 제출하면 AI가 생성한 표지와 줄거리를 바탕으로 도서가 자동으로 등록됩니다[cite: 20].

### 👤 이용자 관점

  * [cite\_start]포인트를 구매하여 원하는 도서를 낱개로 구매하거나, 월 단위 구독 서비스로 무제한 열람할 수 있습니다[cite: 22].

### 🤖 AI 및 플랫폼 관점

  * [cite\_start]AI는 소설의 제목과 내용을 기반으로 즉시 판매 가능한 수준의 표지와 줄거리 요약본을 생성합니다[cite: 27].
  * [cite\_start]플랫폼은 AI가 생성한 도서의 가격을 책정하고, 열람 횟수를 집계하여 베스트셀러를 선정 및 노출시킵니다[cite: 24, 25].

-----

## 📐 아키텍처 및 설계 (Architecture & Design)

### 클라우드 아키텍처 (Cloud Architecture)

[cite\_start]본 서비스는 MSA를 기반으로 하며, 주요 구성 요소는 다음과 같습니다[cite: 39].

  * [cite\_start]**마이크로서비스:** Member, Author, Book, LLM[cite: 45, 46, 47, 48].
  * [cite\_start]**인프라:** API Gateway, Load Balancer, 메시징 채널을 위한 Kafka[cite: 42, 43, 49].
  * [cite\_start]**관찰 가능성 도구:** Jaeger, Kiali, Loki, Grafana, Zipkin을 활용하여 시스템을 모니터링합니다[cite: 50, 51, 52, 53, 54].

\<img src="[https://i.imgur.com/8QO5Kz8.png](https://www.google.com/search?q=https://i.imgur.com/8QO5Kz8.png)" alt="Cloud Architecture Diagram" width="800"/\>

### 이벤트 스토밍 (Event Storming)

[cite\_start]각 도메인의 이벤트, 커맨드, 정책 등을 시각화하여 서비스의 비즈니스 로직을 구체적으로 설계했습니다[cite: 62].

\<img src="[https://i.imgur.com/uG5D5sQ.png](https://www.google.com/search?q=https://i.imgur.com/uG5D5sQ.png)" alt="Event Storming Diagram" width="800"/\>

### 헥사고날 아키텍처 (Hexagonal Architecture)

[cite\_start]각 마이크로서비스는 헥사고날 아키텍처를 적용하여 비즈니스 로직과 외부 기술(Web, DB 등)을 분리함으로써 유연하고 테스트하기 쉬운 구조를 갖추었습니다[cite: 63].

\<img src="[https://i.imgur.com/o1bM59k.png](https://www.google.com/search?q=https://i.imgur.com/o1bM59k.png)" alt="Hexagonal Architecture Diagram" width="800"/\>

-----

## 🛠️ 구현 (Implementation)

### AI 기능 적용

[cite\_start]LLM을 활용하여 사용자가 제출한 원고의 내용에 맞춰 책 표지와 줄거리를 자동으로 생성했습니다[cite: 69].

| 책 제목 | AI 생성 표지 |
| :--- | :---: |
| [cite\_start]**시간의 서랍** [cite: 70] | \<img src="[https://i.imgur.com/r0xIcr1.png](https://www.google.com/search?q=https://i.imgur.com/r0xIcr1.png)" alt="시간의 서랍 표지" width="250"/\> |
| [cite\_start]**달 아래의 기억들** [cite: 73] | \<img src="[https://i.imgur.com/3D1H14W.png](https://www.google.com/search?q=https://i.imgur.com/3D1H14W.png)" alt="달 아래의 기억들 표지" width="250"/\> |

### 이벤트 기반 통신

[cite\_start]서비스 간의 데이터 정합성을 맞추기 위해 Kafka를 이용한 이벤트 기반 비동기 통신(Pub/Sub)을 구현했습니다[cite: 75]. 아래는 Kafka 컨슈머가 이벤트를 수신하는 로그입니다.

\<img src="[https://i.imgur.com/u0b1l2U.png](https://www.google.com/search?q=https://i.imgur.com/u0b1l2U.png)" alt="Kafka Logs" width="800"/\>

-----

## 🚀 배포 및 운영 (Deployment & Operations)

  * [cite\_start]**Kubernetes & Istio:** 모든 마이크로서비스는 Kubernetes 클러스터에 컨테이너 형태로 배포되었으며, 서비스 간 통신은 Istio 서비스 메시로 관리됩니다[cite: 78, 79].
  * [cite\_start]**CI/CD 파이프라인:** 각 서비스의 빌드(CI)와 배포(CD)를 자동화하는 파이프라인을 구축하여 운영 효율성을 높였습니다[cite: 82].
  * [cite\_start]**통합 모니터링:** Grafana 대시보드를 통해 클러스터 리소스와 서비스 상태를 통합적으로 모니터링합니다[cite: 80].

\<img src="https://www.google.com/search?q=https://i.imgur.com/i9o7fI8.png" alt="Kubernetes Deployment Status" width="800"/\>

-----

## 🏃‍♂️ 실행 방법 (How to Run)

이 프로젝트는 **Docker Compose**를 사용하여 로컬 개발 환경을 손쉽게 구성하고 실행할 수 있습니다.

### 사전 요구사항 (Prerequisites)

  * [Docker](https://www.docker.com/get-started)
  * [Docker Compose](https://docs.docker.com/compose/install/)

### 로컬 환경 실행 (Local Setup)

1.  **프로젝트 클론**

    ```bash
    git clone <REPOSITORY_URL>](https://github.com/AI-Publishing-Hub/Walking-Library.git
    cd Walking-Library
    ```

2.  **서비스 빌드 및 컨테이너 실행**
    프로젝트의 루트 디렉터리에 있는 `docker-compose.yml` 파일은 모든 마이크로서비스(`member`, `author`, `book`)와 인프라(`kafka`, `zookeeper`)를 정의합니다.
    아래 명령어를 실행하여 모든 서비스를 한 번에 빌드하고 실행하세요.

    ```bash
    docker-compose up -d --build
    ```

      * `-d`: 컨테이너를 백그라운드에서 실행합니다.
      * `--build`: 이미지를 빌드한 후 컨테이너를 시작합니다.

3.  **서비스 상태 확인**
    아래 명령어로 모든 컨테이너가 정상적으로 실행 중인지 확인할 수 있습니다.

    ```bash
    docker-compose ps
    ```

4.  **서비스 종료**
    프로젝트 실행을 중지하고 관련 리소스를 모두 삭제하려면 다음 명령어를 사용하세요.

    ```bash
    docker-compose down
    ```
