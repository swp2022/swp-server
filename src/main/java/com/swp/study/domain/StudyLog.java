package com.swp.study.domain;
import javax.persistence.*;

@Entity
@Table(name="study_logs")
public class StudyLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_log_id",nullable = false)
    private Long studyLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="study_id",nullable = false)
    private Study study;

    @Column(name="recorded_time",nullable=false)
    private Long recordedTime;

    @Column(name="percentage",nullable = false)
    private Double percentage;
}
