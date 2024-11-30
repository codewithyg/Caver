package com.example.Caver.repository;

import com.example.Caver.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 게시판 데이터베이스 (board 테이블)에 액세스 하기위한 JPA Repository 인터페이스
 */

public interface BoardRepository extends JpaRepository<Board, Integer> {
}
