package com.example.Caver.controller;

import com.example.Caver.model.Board;
import com.example.Caver.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 게시글 목록
    @GetMapping
    public String listBoards(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 10); // 10 posts per page
        Page<Board> boardPage = boardService.getBoards(pageable);

        model.addAttribute("boards", boardPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", boardPage.getTotalPages());

        return "board";
    }

    // 게시글 작성 페이지
    @GetMapping("/new")
    public String showWriteForm() {
        return "boardwrite";
    }

    // 게시글 작성 처리
    @PostMapping("/new")
    public String writeBoard(@RequestParam String writer, @RequestParam String title, @RequestParam String content, @RequestParam String password) {
        Board board = new Board();
        board.setWriter(writer);
        board.setTitle(title);
        board.setContent(content);
        board.setPassword(password);
        boardService.createBoard(board);
        return "redirect:/board";
    }

    // 게시글 상세보기
    @GetMapping("/{boardNo}")
    public String viewBoard(@PathVariable int boardNo, Model model) {
        model.addAttribute("board", boardService.getBoardById(boardNo));
        return "boarddetail";
    }

    // 게시글 수정 페이지
    @PostMapping("/updatecontent/{boardNo}")
    public String showUpdateForm(@PathVariable int boardNo, @RequestParam String password, Model model) {
        try {
            Board board = boardService.showUpdateForm(boardNo, password); // Service에서 검증 후 게시글 반환
            model.addAttribute("board", board);                          // Model에 게시글 추가
            return "boardupdate";                                        // 수정 페이지로 이동
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            return "redirect:/board/" + boardNo;                         // 상세 페이지로 리다이렉트
        }
    }

    // 게시글 수정 처리
    @PostMapping("/update/{boardNo}")
    public String updateBoard(@PathVariable int boardNo, Board updatedBoard, Model model) {
        try {
            boardService.updateBoard(boardNo, updatedBoard);           // Service에서 게시글 업데이트
            return "redirect:/board/" + boardNo;                       // 수정된 게시글 상세 페이지로 리다이렉트
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "수정 실패. 다시 시도해주세요.");
            return "redirect:/board/updatecontent/" + boardNo;                       // 상세 페이지로 리다이렉트
        }
    }

    // 게시글 삭제
    @PostMapping("/delete/{boardNo}")
    public String deleteBoard(@PathVariable int boardNo, @RequestParam String password, Model model) {
        try {
            boardService.deleteBoard(boardNo, password);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            return "redirect:/board/" + boardNo; // 상세 페이지로 리다이렉트
        }
        return "redirect:/board";
    }
}
