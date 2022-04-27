package com.swp.common.domain;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "studies")
public class Study {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id",nullable = false)
    private Long studyId;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private Long userId;

    @Column(name="start_at",nullable=false)
    @CreatedDate
    private LocalDateTime startAt;

    @Column(name="end_at", nullable = false)
    private LocalDateTime endAt;
}
