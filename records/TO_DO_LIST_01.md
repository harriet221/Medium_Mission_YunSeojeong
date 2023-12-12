# Medium Mission - first period
### : 23.12.05 - 14

-----

commit message:
- no entity func : ACTION
- README : ACTION

ACTION : CREATE 생성 / MODIFY 수정 / UPDATE 구조 변경 / CHECK 미션 진척도 체크

-----

## 필수 미션 1 : 회원기능
### 엔드 포인트

- [x] 가입
  - [x] GET /member/join : 가입 폼
  - [x] POST /member/join : 가입 폼 처리

- [x] 로그인
  - [x] GET /member/login : 로그인 폼
  - [x] POST /member/login : 로그인 폼 처리

- [x] 로그아웃
  - [x] POST /member/logout : 로그아웃

### 폼

- [x] 회원가입 폼
  - username
  - password
  - passwordConfirm

- [x] 로그인 폼
  - username
  - password

-----

## 필수 미션 2 : 글 CRUD
### 엔드 포인트

- [ ] 홈
  - GET / : 홈
    - 최신글 30개 노출

- [x] 글 목록 조회
  - GET /post/list : 전체 글 리스트
    - 공개된 글만 노출

- [ ] 내 글 목록 조회
  - GET /post/myList : 내 글 리스트

- [x] 글 상세내용 조회
    - GET /post/1 : 1번 글 상세보기

- [x] 글 작성
  - [x] GET /post/write : 글 작성 폼
  - [x] POST /post/write : 글 작성 처리

- [x] 글 수정
  - [x] GET /post/1/modify : 1번 글 수정 폼
  - [ ] PUT /post/1/modify : 1번 글 수정 폼 처리

- [x] 글 삭제
  - DELETE /post/1/delete : 1번 글 삭제

- [ ] 특정 회원의 글 모아보기
  - [ ] GET /b/user1 : 회원 user1 의 전체 글 리스트
  - [ ] GET /b/user1/3 : 회원 user1 의 글 중에서 3번글 상세보기

### 폼
- [ ] 글 쓰기 폼
    - title
    - body
    - isPublished
      - 체크박스
      - value="true"

- [ ] 글 수정 폼
  - title
  - body
  - isPublished
    - 체크박스
    - value="true"

---

## Progress Records

### 23.12.06 - original repo
- 점프 투 스프링부트 기반으로 미션 시작.
- Post 관련하여 홈(shwMain)과 목록 조회(showList) 연관 기능 구현

### 23.12.07 - demo repo
- **계획 변경**
- [점프 투 스프링 부트](https://wikidocs.net/160543) 클론 코딩 시작
- 이후 [필수 미션](https://www.scode.gg/p/13201) 따라 수정 예정
- 2-13에서 부트스트랩 적용 시작 → 이후 변경 가능성 있음
- 진척도: ~ 2-13

### 23.12.08 - demo repo
- 3-02 페이징 구현에서 맨 앞/맨 뒤 버튼 추가
- 3-04 댓글수 표시에서 빨간 글씨 마음에 안들어서 검은색 [n] 형태로 변경
- 진척도: ~ 3-04

### 23.12.11 - demo repo
- MemberController 만드는 중, PostController와 CommentController에서 Form 클래스를 컨트롤러 내부에 @Data로 만들었는데 Form 필드를 불필요하게 getter로 사용하고 있는 것을 발견하여 직접 사용하는 것으로 수정
- MemberRole이 필요한가 싶은데 어떻게 뺄지 몰라서 일단 넣음 (차후 수정 고려)
- 3-07 navbar JOIN에도 sec:authorize="isAnonymous()" 적용 (로그인 중에 안 보이게)
- 3-10 post_detail에서 th:text="게시글 수정"으로 작성한 수정 버튼에서 에러 발생 → th:text="'게시글 수정'" 으로 수정해 해결 :
  `Thymeleaf에서 th:text 속성을 사용할 때, 띄어쓰기를 포함하는 문자열을 설정하려면 작은 따옴표로 감싸야 함`
- 3-10 modifyPost에서 postForm 객체에 대한 유효성 검증이 실패했다는 에러 발생 → chatGPT를 통해 해결 :
  `/modify/{id}에 대한 GET 요청이 들어왔을 때 modifyPost 메서드에서 PostForm을 초기화하고 post_form을 보여주는데, 이 때 postForm 객체가 바인딩되면서 title과 content가 null로 초기화 되는 문제`
  `GET 메서드에서는 post_form을 보여줄 뿐, PostForm 초기화를 하지 않도록 수정함`
- 3-10 위 이슈 해결하고 나니까, 게시글 수정시 기존 내용이 보이지 않는 문제 발생 → model은 그대로 사용하되, 기존 제목과 내용을 get으로 받아서 넣은 postForm 객체를 add 하는 것으로 수정해 해결
- 진척도: ~ 3-10

### 23.12.12 - original repo
- original repo 복귀 / demo 파일 전부 copy & paste
- 지금까지의 진척도 테스트 완료
- 테스트 데이터 넣는 게 안돼서 수동으로 넣음...
- Jump to Springboot 방식의 URL 필수미션 요구사항 반영해 변경
  - post/detail/id → post/id
  - post/modify/id → post/id/modify
  - post/delete/id → post/id/delete
  - comment도 마찬가지