네, 이미지의 출처 페이지를 표기하고 다른 주석(\`\`)은 모두 제거하여 바로 복사-붙여넣기 할 수 있도록 수정했습니다.

-----

# KT 걷다가서재 (Walking-Library)

> KT AIVLE School 미니프로젝트 최종 수행 보고서.
> **팀:** 9반 24조 [예스24]



## 📝 프로젝트 개요 (Overview)

\*\*'걷다가서재'\*\*는 MSA(Microservice Architecture) 기반의 웹소설 출판 플랫폼입니다. 작가가 소설을 집필하면, LLM AI가 자동으로 책 표지와 줄거리를 생성하여 출판을 돕는 서비스를 제공합니다. 독자들은 포인트를 충전하여 작품을 개별 구매하거나 월 구독 서비스를 통해 무제한으로 열람할 수 있습니다.



## ✨ 주요 기능 (Features)

### ✒️ 작가 관점

  * 작가로 등록 신청하고 승인받은 후 작품을 출판할 수 있습니다.
  * 웹 에디터를 통해 글을 작성하고 임시 저장하거나 최종 제출할 수 있습니다.
  * 원고를 제출하면 AI가 생성한 표지와 줄거리를 바탕으로 도서가 자동으로 등록됩니다.

### 👤 이용자 관점

  * 포인트를 구매하여 원하는 도서를 낱개로 구매하거나, 월 단위 구독 서비스로 무제한 열람할 수 있습니다.

### 🤖 AI 및 플랫폼 관점

  * AI는 소설의 제목과 내용을 기반으로 즉시 판매 가능한 수준의 표지와 줄거리 요약본을 생성합니다.
  * 플랫폼은 AI가 생성한 도서의 가격을 책정하고, 열람 횟수를 집계하여 베스트셀러를 선정 및 노출시킵니다.

-----

## 📐 아키텍처 및 설계 (Architecture & Design)

### 클라우드 아키텍처 (Cloud Architecture)

본 서비스는 MSA를 기반으로 하며, 주요 구성 요소는 다음과 같습니다.

  * **마이크로서비스:** Member, Author, Book, LLM.
  * **인프라:** API Gateway, Load Balancer, 메시징 채널을 위한 Kafka.
  * **관찰 가능성 도구:** Jaeger, Kiali, Loki, Grafana, Zipkin을 활용하여 시스템을 모니터링합니다.

![image](https://github.com/user-attachments/assets/3bde832c-3395-4fc7-8a4d-bdd0c82d8c94)


### 이벤트 스토밍 (Event Storming)

각 도메인의 이벤트, 커맨드, 정책 등을 시각화하여 서비스의 비즈니스 로직을 구체적으로 설계했습니다.

![image](https://github.com/user-attachments/assets/2f12eb48-05a6-414a-8978-b3ba7a11f6b8)


### 헥사고날 아키텍처 (Hexagonal Architecture)

각 마이크로서비스는 헥사고날 아키텍처를 적용하여 비즈니스 로직과 외부 기술(Web, DB 등)을 분리함으로써 유연하고 테스트하기 쉬운 구조를 갖추었습니다.

![image](https://github.com/user-attachments/assets/58d5db3e-f552-44d0-a1de-2713452e834b)

-----

## 🛠️ 구현 (Implementation)

### AI 기능 적용

LLM을 활용하여 사용자가 제출한 원고의 내용에 맞춰 책 표지와 줄거리를 자동으로 생성했습니다.

| 책 제목 | AI 생성 표지 |
| :--- | :---: |
| **시간의 서랍** | ![image](https://github.com/user-attachments/assets/a47c20b7-8843-4ec6-b62c-279c32bc709b) |
| **달 아래의 기억들** | ![image](https://github.com/user-attachments/assets/c01762fa-a6e1-44a3-8630-70942e7d852c) |

### 이벤트 기반 통신

서비스 간의 데이터 정합성을 맞추기 위해 Kafka를 이용한 이벤트 기반 비동기 통신(Pub/Sub)을 구현했습니다. 아래는 Kafka 컨슈머가 이벤트를 수신하는 로그입니다.

![image](https://github.com/user-attachments/assets/21f25f16-9b66-404a-adba-486cac221f01)


-----

## 🚀 배포 및 운영 (Deployment & Operations)

  * **Kubernetes & Istio:** 모든 마이크로서비스는 Kubernetes 클러스터에 컨테이너 형태로 배포되었으며, 서비스 간 통신은 Istio 서비스 메시로 관리됩니다.
  * **CI/CD 파이프라인:** 각 서비스의 빌드(CI)와 배포(CD)를 자동화하는 파이프라인을 구축하여 운영 효율성을 높였습니다.
  * **통합 모니터링:** Grafana 대시보드를 통해 클러스터 리소스와 서비스 상태를 통합적으로 모니터링합니다.
![image](https://github.com/user-attachments/assets/5e506c0b-01ba-4dc5-b476-7d9c89a033b2)

![image](https://github.com/user-attachments/assets/928b1a9a-dd23-4845-af49-fb03a0f0893e)

-----

## 🏃‍♂️ 실행 방법 (How to Run)

이 프로젝트는 **Docker Compose**를 사용하여 로컬 개발 환경을 손쉽게 구성하고 실행할 수 있습니다.

### 사전 요구사항 (Prerequisites)

  * [Docker](https://www.docker.com/get-started)
  * [Docker Compose](https://docs.docker.com/compose/install/)

### 로컬 환경 실행 (Local Setup)

1.  **프로젝트 클론**

    ```bash
    git clone https://github.com/AI-Publishing-Hub/Walking-Library.git
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
