package com.example.Caver.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시판 게시글 데이터 저장하는 DTO
 * boardNO : 게시글 번호
 * writer : 작성자
 * title : 제목
 * content : 내용
 * regDate : 게시글 등록 날짜
 * password : 게시글 작성 시 비밀번호 (암호화 저장)
 */

@Getter
@Setter
@Entity
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardNo;

    @Column(nullable = false, length = 50)
    private String writer;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(length = 1000)
    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime regDate;

    @PrePersist
    public void setDefaultRegDate() {
        if (this.regDate == null) {
            this.regDate = LocalDateTime.now(); // 현재 시간 설정
        }
    }

    @Column(nullable = false, length = 50)
    private String password;

}
