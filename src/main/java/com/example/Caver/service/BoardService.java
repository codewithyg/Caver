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

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Page<Board> getBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Board getBoardById(int boardNo) {
        return boardRepository.findById(boardNo)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
    }

    public void createBoard(Board board) {
        String encodedPassword = passwordEncoder.encode(board.getPassword()); // 비밀번호 암호화
        board.setPassword(encodedPassword);
        boardRepository.save(board);
    }

    public Board showUpdateForm(int boardNo, String password) {
        Board board = getBoardById(boardNo);   // 게시글 조회
        validatePassword(password, board);     // 비밀번호 검증
        return board;
    }

    public void updateBoard(int boardNo, Board updatedBoard) {
        Board board = getBoardById(boardNo); // 게시글 조회
        board.setTitle(updatedBoard.getTitle());
        board.setContent(updatedBoard.getContent());
        boardRepository.save(board);        // 게시글 저장
    }

    public void deleteBoard(int boardNo, String password) {
        Board board = getBoardById(boardNo);
        validatePassword(password, board);
        boardRepository.deleteById(boardNo);
    }

    // 비밀번호 검증을 위한 공통 메소드
    private void validatePassword(String password, Board board) {
        if (!passwordEncoder.matches(password, board.getPassword())) { // 입력된 비밀번호와 DB에 저장된 비밀번호 비교
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }
}
