# Medium Mission - second period
### : 23.12.26 - 01.04

-----

commit message:
- no entity func : ACTION
- README : ACTION

ACTION : CREATE 생성 / MODIFY 수정 / UPDATE 구조 변경 / CHECK 미션 진척도 체크

-----
## 목표 : 멤버십 기능 + 정산 기능 구현

## 필수 미션 1 : Member 클래스에 private boolean isPaid 필드 추가
- [ ] 해당 필드가 true 인 사람이 로그인할 때, ROLE_PAID 권한도 가지도록(스프링 시큐리티)
- [ ] 해당 필드가 true 이면 유료 멤버십 회원 입니다.

-----

## 필수 미션 2 : Post 클래스에 private boolean isPaid 필드 추가

- [ ] 해당 필드가 true 인 글은 유료회원이 아닌사람에게는 상세보기(GET /post/1)에서 본문(content)이 나올 자리에 '이 글은 유료멤버십전용 입니다.' 라는 문구가 나온다.

### 엔드 포인트
- [ ] GET /post/list
  - [ ] 멤버십 회원이 아니라도 글 리스트에서는 멤버십 전용글을 볼 수 있습니다.

- [ ] GET /post/1
  - [ ] 유료 멤버십 회원이고 1번 글이 멤버십전용글 이라면 볼 수 있다.
  - [ ] 그 외에는 '이 글은 유료멤버십전용 입니다.' 노출

-----

## 필수미션 3 : NotProd 에서 유료멤버십 회원(샘플 데이터)과 유료글(샘플 데이터)을 각각 100개 이상 생성

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