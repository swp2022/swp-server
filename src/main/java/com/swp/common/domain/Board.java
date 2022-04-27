package com.swp.common.domain;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id",nullable = false)
    private Long boardId;

    @ManyToOne
    @JoinColumn(name ="user_id",nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name ="study_id",nullable = false)
    private Long studyId;

    @Column(name="content",length=1000,nullable = true)
    private String content;

    @Column(name="created_at",nullable=false)
    @CreatedDate
    private LocalDateTime createdAt;


}
