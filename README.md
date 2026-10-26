# Caver

Spring Boot를 활용해 개발한 포털형 웹 애플리케이션입니다.

클라우드시스템 수업의 개인 프로젝트로, 네이버와 같은 포털 사이트를 참고하여 뉴스, 날씨, 주식 정보 조회 및 게시판 기능을 구현했습니다.

## 주요 기능

- **뉴스:** 외부 API를 활용한 국내외 뉴스 및 카테고리별 기사 조회
- **날씨:** 지역 검색을 통한 현재 날씨 및 예보 조회
- **주식:** 종목 검색 및 주가 정보 조회
- **게시판:** 게시글 등록, 조회, 수정, 삭제 및 페이징

## 기술 스택

| 구분 | 기술 |
|---|---|
| Language | Java 21 |
| Backend | Spring Boot 3.3.5, Spring MVC, Spring Data JPA |
| Frontend | Thymeleaf, HTML, CSS, JavaScript |
| Database | MySQL |
| Cloud | Google Cloud Platform (GCP) |

## 개발 환경

- Gradle 기반 프로젝트
- 외부 API를 활용한 뉴스·날씨·주식 데이터 조회
- GCP 가상머신에서 JAR 파일을 실행하여 애플리케이션 동작 확인

## 참고 사항

학부 수업 중 학습용 프로젝트입니다. 현재 별도로 운영하고 있지 않으며, 외부 API의 인증 정보 및 서비스 상태에 따라 일부 기능이 정상적으로 동작하지 않을 수 있습니다.
