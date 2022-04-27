package com.swp.common.domain;

import javax.persistence.*;

@Entity
@Table(name="study_logs")
public class StudyLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_log_id",nullable = false)
    private Long studyLogId;

    @ManyToOne
    @JoinColumn(name ="study_id",nullable = false)
    private Long studyId;

    @Column(name="recorded_time",nullable=false)
    private Long recordedTime;

    @Column(name="percentage",nullable = false)
    private double percentage;
}
