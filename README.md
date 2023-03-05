# StackOverflow Clone Coding Refactoring

### 개요

- CodeStates Software Engineering Bootcamp에서 진행한 Pre-Project(22.08.23 ~ 09.06) 의 내용을 리팩토링 하는 것을 목표로 함
  - 제가 담당했던 Backend 부분으로 한정하여 진행함

- Pre-Project 설명
  - 주제 : **StackOverflow Clone Coding**
  - 내용 : Frontend는 React, Backend는 Spring을 이용해 기존에 있는 StackOverflow 웹 사이트의 Clone을 만들어본다.
  - 관련 문서 : [요구사항 명세서](https://docs.google.com/spreadsheets/d/1TU4THwrGQrICJ5WpIfY0uErBtV6B2lUz78Vv8mRqHBo/edit?usp=sharing), [테이블 명세서](https://docs.google.com/spreadsheets/d/1Yg-kKvSE6SDEX1lCuml5SNfR6vANYRMWO88cIARptgg/edit?usp=sharing) 

### 목표
- public method 마다 Unit Test를 작성하여 일부 코드 변경시 다른 class에 번지는 여파를 알 수 있게 한다.
- 다른 사람이 읽기 수월하도록 Code Convention을 정하고, 이를 지키며 리팩토링을 한다.
- 리팩토링시 떠오르는 개선사항들을 기록하고 개선해본다.
- 시간이 된다면, 요구사항 명세서 Advenced에 있는 내용을 구현해본다.

### Sprint 1 (23.02.05 ~ 02.13) : Mapstruct Refactoring

- 다수의 회사들에서 사용하는 Jira를 사용해보고 익히기
- Mapstruct 관련 interface Unit Test를 작성하기
- Mapstruct 관련 interface 리팩토링 실시
  - 레퍼런스를 참고하여 최대한 라이브러리의 내용을 이용할 것
- Git Convention 을 정하고 이를 지키서 commit 실시

### Sprint 2 (23.02.17 ~ 03.05) : Service Layer Refactoring 1

- Code Convention 완성, Git branch 관리 방법 작성
- Member, Question, Answer domain의 Service Layer Refactoring
  - Service 관련 code의 Unit Test 작성
  - Service 관련 code를 Code Convention에 맞추어 리팩토링 실시
- helper package에 있는 code들 Unit Test 작성 & 리팩토링 실시
  - EmailSender, PasswordCreator, S3ImageUpload

### Sprint 3 (23.03.13 ~ 03.26) : Service Layer Refactoring 2

- Follow, Vote 관련 Service Layer Refactoring
  - Service 관련 code의 Unit Test 작성
  - Service 관련 code를 Code Convention에 맞추어 리팩토링 실시
- Service Layer 분리
  - 한가지 Service에 많은 책임이 할당되어 있어 분리 실시
  - interface 제거 예정 (다른 방식으로 구현될 일이 없다고 생각함)
- helper Refactoring
  - upload : Unit test가 가능하도록 구조 변경
  - password : interface 구현 (다른 방법으로도 구현 가능하다고 생각)
  - email : 필요없는 파일 제거, TemporaryEmailSender interface 구현, Unit test가 가능하도록 구조 변경

### Code Convention
- Basic Code Convention
  - 기본적으로 [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) 를 따름
  - Wiki에 추가 예정

- Refactoring Convention
  - 함수(또는 메서드)의 길이가 15라인을 넘어가지 않도록 구현한다.
    - 함수(또는 메서드)가 한 가지 일만 하도록 최대한 작게 만들어라.
  - 메서드의 파라미터 개수는 최대 3개까지만 허용한다.
  - indent(인덴트, 들여쓰기) depth를 3이 넘지 않도록 구현한다. 2까지만 허용한다.
      - 예를 들어 while문 안에 if문이 있으면 들여쓰기는 2이다.
  - 3항 연산자를 쓰지 않는다.
  - JUnit 5와 AssertJ를 이용하여 본인이 정리한 기능 목록이 정상 동작함을 테스트 코드로 확인한다.
  - else 예약어를 쓰지 않는다.
    - switch/case는 최소한으로 사용한다.

### Git Convention

#### Git Message Convention
- 전반적으로 [해당 링크](https://gist.github.com/stephenparish/9941e89d80e2bc58a153)의 규칙을 따른다.
    ```text
    <issue key> <type>(<scope>): <subject>
    <BLANK LINE>
    <body>
    <BLANK LINE>
    <footer>
    ```
  - issue key : Jira에서 사용하는 Issue의 key를 넣음
  - type : 해당 커밋의 타입을 적음
    - feat, fix, docs, style, refactor, test, chore
  - scope : 범위를 지정하는 모든 것이 될 수 있다[
    - 파일명, 도메인명, location, browser, complie ...]()
  - subject : 현재 작업을 간략히 한줄로 요약
  - body : 변경에 대한 동기에 대해 작성, 이전과 달라진 점 작성
  - footer : 주요 변경 사항, ...

#### 브랜칭 전략
- main branch에 현재까지 작업한 내용이 보일 수 있도록 함
- 기능 구현 시에는 main에서 브랜치를 생성해서 작업 후, 다시 main으로 PR을 하는 형식으로 진행
- README 수정과 같은 문서 작업 등은 따로 branch를 생성하지 않고 main에서 작업하는 것도 허용함
  - Auto Deploy와 같은 작업을 하지 않았기 때문에 main에서 직접 커밋을 허용

#### Branch Naming Convention
- Jira의 Issue에 branch를 참조하기 위해 다음과 같은 형식 사용
  ```
  <issue key>/<type>/<scope>
  ```
    - issue key : Jira에서 사용하는 Issue의 key를 넣음
    - type : feat, refactor, chore 등등
    - scope : 작업 대상 작성 (형식 : snake_case)
    - ex) SCR-17/refactor/member_service

### 기타
- README는 아직 미완성이므로 중간중간 업데이트 될 수 있습니다.
- [문제 해결 & 회고록](https://velog.io/@gwichanlee/series/StackOverflow-Clone-Refactoring)