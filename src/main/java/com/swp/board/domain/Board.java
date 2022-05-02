package com.swp.board.domain;
import com.swp.common.domain.CreatedAtEntity;
import com.swp.study.domain.Study;
import com.swp.user.domain.User;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "boards")
public class Board extends CreatedAtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", nullable = false)
    private Integer boardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="study_id", nullable = false)
    private Study study;

    @Column(name = "content", length = 1000, nullable = true)
    private String content;

    @OneToMany( mappedBy = "board")
    private List<Comment> commentList = new ArrayList<>();
}
