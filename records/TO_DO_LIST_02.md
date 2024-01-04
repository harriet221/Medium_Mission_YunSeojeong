# Medium Mission - second period
### : 23.12.26 - 24.01.04

-----

commit message:
- no entity func : ACTION
- README : ACTION

ACTION : CREATE 생성 / MODIFY 수정 / UPDATE 구조 변경 / CHECK 미션 진척도 체크

-----
## 목표 : 멤버십 기능 + 정산 기능 구현

## 필수 미션 1 : Member 클래스에 private boolean isPaid 필드 추가
- [x] 해당 필드가 true 인 사람이 로그인할 때, ROLE_PAID 권한도 가지도록(스프링 시큐리티)
- [x] 해당 필드가 true 이면 유료 멤버십 회원 입니다.

-----

## 필수 미션 2 : Post 클래스에 private boolean isPaid 필드 추가

- [x] 해당 필드가 true 인 글은 유료회원이 아닌사람에게는 상세보기(GET /post/1)에서 본문(content)이 나올 자리에 '이 글은 유료멤버십전용 입니다.' 라는 문구가 나온다.

### 엔드 포인트
- [x] GET /post/list
  - [x] 멤버십 회원이 아니라도 글 리스트에서는 멤버십 전용글을 볼 수 있습니다.

- [x] GET /post/1
  - [x] 유료 멤버십 회원이고 1번 글이 멤버십전용글 이라면 볼 수 있다.
  - [x] 그 외에는 '이 글은 유료멤버십전용 입니다.' 노출

-----

## 필수미션 3 : NotProd 에서 유료멤버십 회원(샘플 데이터)과 유료글(샘플 데이터)을 각각 100개 이상 생성
- [x] 구현 완료

---

## Progress Records

### ~ 23.12.24
- First 작업 + refactoring : [first period README](TO_DO_LIST_01.md)

### 23.12.26
- first 부분 작업중 안됐던 부분 해결
  - isPublished DB에 반영 안 되었던 부분 해결
  - h2-console 로그인에서 JDBC URL에 jdbc:h2/local을 넣어서 생긴 문제. 이전에 Jump To SpringBoot 기반으로 만들면서 거기서 설정했던 DB URL을 그대로 썼더니 그런 모양.
  - JDBC URL에 jdbc:h2:./medium_dev;MODE=MYSQL (application.yml의 URL)을 넣고 로그인하니 해결되었다. (feat. 우철승 멘토님)
  - ~~다만, checkbox에 체크 되어있는 (true) 모양을 디폴트 값으로 놓으려고 했는데 그 부분은 실패함.~~ 해결 : PostForm에서 isPublished = true로 설정.

### 23.12.27
- Member 클래스에 isPaid 필드 추가
  - MemberRole에 PAID 값 추가
  - MemberSecurityService에 isPaid가 true인 유저의 경우, USER와 PAID 권한 모두 가지도록 설정
  - 회원으로 가입할 때는 디폴트로 false 값 가지도록 설정

### 23.12.28
- Post 관련 목록 보여주는 모든 부분에 isPublished 관련 부분 적용
  - PostRepository에서 findAll과 findByAuthor 메서드는 **Spring Data JPA에서 제공하는 Query Creation 기능에 의해 자동으로 생성**되는 메소드임.
  - 그래서 처음에는 PostRepository에서 상속받은 JpaRepository와 Spring을 믿고 findAllIsPublished 등으로 자동 구현되게 하려고 했으나, <br>
    에러를 여러번 겪고나서 isPublished 관련 find 메서드는 Spring 측에서 자동으로 만들 수 없다는 결론을 내림.
  - @Query 통해 PostRepository에서 직접 쿼리문을 작성하여 findAllIsPublished, findByAuthorIsPublished 메서드 구현
  - 결과물 
    1. showMain과 showList에서는 공개된 post만 보임 (비회원에게도 노출)
    2. showYourList에서는 해당 저자의 공개된 post만 보임 (비회원에게도 노출)
    3. showMyList에서는 나의 비공개 + 공개 post 함께 보임 (로그인 했을 때 나에게만 노출)

### 24.01.03
- Post 멤버십 전용 포스트 열람 제한 적용
  - 유료 멤버십 포스트로 작성된 포스트일 때,
  다음에 해당하는 경우 포스트 내용 대신 `※ 멤버십 포스트 입니다.` 출력
    1. 로그인 하지 않은 비회원
    2. 로그인 했으나 멤버십에 가입하지 않은 회원
  - 예외: 로그인 한 회원이 해당 포스트의 작성자 본인인 경우
- Sample Data 생성용 클래스 구현
  - /global/init/NotPod.java
  - 멤버십 회원 user99와 멤버십 포스트 1~100 생성