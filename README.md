원티드 클론 서버

### - 2022.06.25  
ERD 설계 - https://dbdiagram.io/d/62b6c20769be0b672c3d4633  
API 명세서 - Index 작성  
EC2 & RDS 인스턴스 생성  

### - 2022.06.26 
서브 도메인 생성 - dev.odoong.shop / prod.odoong.shop  
SSL 적용  
API 명세서 수정 - SMS 인증 서비스 추가 
API 명세서 작성 및 구현 - 회원가입/로그인/SMS 인증  

### - 2022.06.27
API 명세서 작성 및 구현 - 채용공고 조회/필터/상세 조회  
CORS 이슈 - CorsFilter 재정의 통해 해결  

### - 2022.06.28
API 명세서 추가 - 이메일 검증 추가  
AWS Hikari pool 이슈 - 드라이버 클라이언트 변경 시도  

### - 2022.06.29
API 명세서 구현 - 북마크 생성
API 명세서 수정 - 채용공고 조회 부분 FE 분들과 협의한 대로 수정
ERD에 맞춰 Entity 구성  
AWS x -> JPA Hikari pool 이슈 - 아직 있는 듯 함. java 버전이 원인일 수 있어 java8로 변경함.

### - 2022.06.30
API 명세서 구현 - 좋아요 생성/ 팔로우 생성  
이력서, 회사, 지원 기능 부분 구현 로컬 테스트 중  
AWS -> 성능 이슈로 인한 오류였음 - swap 영역 설정 통해 해결함  
내일 할 일 - API 명세서 작성 및 ERD 추가, 마이페이지 부분 및 이미지 업로드 API 시도

### - 2022.07.01
API 명세서 작성 - 이력서 CRU 기능  
ERD 추가 - Language certificate

### - 2022.07.02
API 명세서 작성 - 이력서, 회사 부분
JPA 활용한 이력서 항목 저장 구현

### - 2022.07.03
API 명세서 수정 - 이력서 스킬 추가, 채용 공고 조회 응답 추가, 기업 서비스 분리  
직군, 직무 등의 데이터 형식 협의 후에 결정하고, DB에 반영  
이미지 파일 업로드 API 추가

### - 2022.07.04 커밋 까먹음..

### - 2022.07.05
API 명세서 추가 - 커뮤니티 게시글, 댓글, 좋아요 기능 생성
