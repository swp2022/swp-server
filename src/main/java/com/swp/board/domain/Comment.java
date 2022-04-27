package com.swp.board.domain;
import com.swp.user.domain.User;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id",nullable = false)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name ="board_id",nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name ="user_id",nullable = false)
    private User user;

    @Column(name="content",length=1000,nullable = true)
    private String content;

    @Column(name="created_at",nullable=false)
    @CreatedDate
    private LocalDateTime createdAt;
}
