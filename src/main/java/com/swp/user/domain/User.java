package com.swp.user.domain;
import com.swp.board.domain.Board;
import org.springframework.data.annotation.CreatedDate;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",nullable = false)
    private Long userId;

    @Column(name="email",length=100,nullable = false,unique = true)
    private String email;

    @Column(name="nickname",length=100,nullable=false,unique = true)
    private String nickname;

    @Column(name="created_at",nullable=false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name="deleted_at", nullable = true)
    private LocalDateTime deletedAt;

    @OneToMany( mappedBy = "user")
    private List<Board> boardList = new ArrayList<Board>();
}
