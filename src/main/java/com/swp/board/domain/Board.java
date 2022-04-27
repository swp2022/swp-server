package com.swp.board.domain;
import com.swp.study.domain.Study;
import com.swp.user.domain.User;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id",nullable = false)
    private Long boardId;

    @ManyToOne
    @JoinColumn(name ="user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name ="study_id",nullable = false)
    private Study study;

    @Column(name="content",length=1000,nullable = true)
    private String content;

    @Column(name="created_at",nullable=false)
    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany( mappedBy = "board")
    private List<Comment> commentList = new ArrayList<>();
}
