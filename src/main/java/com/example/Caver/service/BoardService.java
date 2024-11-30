package com.example.Caver.service;

import com.example.Caver.model.Board;
import com.example.Caver.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

/**
 * 게시판 관련 로직을 처리하는 클래스
 */

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 전체 게시글 반환
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    // 페이징 처리된 게시글 반환
    public Page<Board> getBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    // 게시글 번호를 사용한 게시글 조회
    public Board getBoardById(int boardNo) {
        return boardRepository.findById(boardNo)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
    }

    // 게시글 작성
    // 비밀번호는 암호화 처리 후 DB에 저장
    public void createBoard(Board board) {
        String encodedPassword = passwordEncoder.encode(board.getPassword()); // 비밀번호 암호화
        board.setPassword(encodedPassword);
        boardRepository.save(board);
    }

    // 수정을 원하는 게시글을 찾고 비밀번호 검증
    // 검증 통과 시 게시글 반환
    public Board showUpdateForm(int boardNo, String password) {
        Board board = getBoardById(boardNo);   // 게시글 조회
        validatePassword(password, board);     // 비밀번호 검증
        return board;
    }

    // 게시글을 수정하고 DB에 변경사항 저장
    public void updateBoard(int boardNo, Board updatedBoard) {
        Board board = getBoardById(boardNo); // 게시글 조회
        board.setTitle(updatedBoard.getTitle());
        board.setContent(updatedBoard.getContent());
        boardRepository.save(board);        // 게시글 저장
    }

    // 게시글 삭제
    public void deleteBoard(int boardNo, String password) {
        Board board = getBoardById(boardNo);
        validatePassword(password, board);
        boardRepository.deleteById(boardNo);
    }

    // 비밀번호 검증을 위한 공통 메서드
    private void validatePassword(String password, Board board) {
        if (!passwordEncoder.matches(password, board.getPassword())) { // 입력된 비밀번호와 DB에 저장된 비밀번호 비교
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }
}
