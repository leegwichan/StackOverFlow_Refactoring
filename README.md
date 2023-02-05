# StackOverflow Clone Coding Refactoring

### 개요

- CodeStates Software Engineering Bootcamp에서 진행한 Pre-Project(22.08.23 ~ 09.06) 의 내용을 리팩토링 하는 것을 목표로 함
  - 주제 : **StackOverflow Clone Coding**
  - 상세 : Spring / Java 로 구성한 Backend 부분을 리팩토링 하는 것을 목표로 함
  - [요구사항 명세서](https://docs.google.com/spreadsheets/d/1TU4THwrGQrICJ5WpIfY0uErBtV6B2lUz78Vv8mRqHBo/edit?usp=sharing), [테이블 명세서](https://docs.google.com/spreadsheets/d/1Yg-kKvSE6SDEX1lCuml5SNfR6vANYRMWO88cIARptgg/edit?usp=sharing) 

### 1차 목표 (23.02.05 ~)

- 다수의 회사들에서 사용하는 Jira를 사용해보고 익히기
- 각 부분마다 Unit Test를 작성하여 한 부분 변경시 다른 부분에 번지는 여파를 알 수 있도록 함
- 기능에만 중점을 두고 작성했던 코드들을 보기 유용하게 작성하는 것을 목표로 함
  - 리팩토링을 진행하고 중점을 두고 생각했던 점 등을 정리하기
- Code Convention/ Git Convention 을 정하고 이를 지키서 코드를 작성함

### Code Convention
- 기본적인 Convention
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

- 전반적으로 [해당 링크](https://gist.github.com/stephenparish/9941e89d80e2bc58a153)의 규칙을 따른다.
    ```text
    <issue key> <type>(<scope>): <subject>
    <BLANK LINE>
    <body>
    <BLANK LINE>
    <footer>
    ```
  - issue key : Jira에서 사용하는 Issue의 key를 넣음
    - Jira에서 자동으로 commit과 issue가 연결되어 보기 용이하게 함
  - type : 해당 커밋의 타입을 적음
    - feat (기능 구현), fix (버그 수정), docs (문서화), style (형식 맞추기, 세미콜론 넣기, …), refactor (리팩토링), test (미처 추가하지 못한 테스트 및 테스트 케이스 추가), chore (유지 보수)
  - scope : 범위를 지정하는 모든 것이 될 수 있다[
    - 파일명, 도메인명, location, browser, complie ...]()
  - subject : 현재 작업을 간략히 한줄로 요약
  - body : 변경에 대한 동기에 대해 작성, 이전과 달라진 점 작성
  - footer : 주요 변경 사항, ...

### 기타
- README는 아직 미완성이므로 중간중간 업데이트 될 수 있습니다.