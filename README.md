
# Built with

*Back-end*:     Spring Boot, Spring MVC, Spring Security, JPA

*Front-end*:    Javascript, React.js

*Server*:       AWS  (EC2, RDS)

*DB*:            MariaDB

*Test*:              Junit5, Postman

Etc.               Swagger

# Features

- 일주일간 조회수, 댓글 수, 좋아요 수, 스크랩 수를 분석해 인기 있는 카드 상위 노출
- 카드에 태그(#) 달기
- 구글 소셜로그인 (Session)
- 태그 검색 (태그 검색 시에도 인기 있는 카드 상위 노출)
- 카드집으로 카드 수집 가능
- 기본적인 카드 CRUD, 댓글, 대댓글, 좋아요, 스크랩, 신고 기능
- 댓글, 대댓글, 좋아요, 스크랩 발생 시 알림 기능
- 에러 메세지 제공

# Convention

### 1. 통일된 Response JSON

```json
{
    "success": boolean,
    "code": number,
    "msg": "string",
    "data": object
}
```

- success: 성공 실패 여부를 작성합니다.
- message: 성공메세지 혹은 에러메세지를 작성합니다.
- code: 에러에 할당되는 코드값을 작성합니다. (0이상 성공, 0미만 실패)
- data: 요청한 데이터 객체를 담습니다.

### 2. Error Code

- -100: 알 수 없는 내부 에러.
- -101: 존재하지 않는 회원.
- -102: 존재하지 않는 카드.
- -103: 존재하지 않는 카드집.
- -201: 로그인 되어있지 않음.
- -202: 접근 권한 없음.
- -301: Request Parameter 매칭 실패 에러.
- -302: Request Parameter 누락 에러.

