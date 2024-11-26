package com.example.Caver.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.Getter;
import lombok.Setter;

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
