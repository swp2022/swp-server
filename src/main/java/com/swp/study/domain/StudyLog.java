package com.swp.study.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "study_logs")
@Getter
public class StudyLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "study_log_id", nullable = false)
	private Integer studyLogId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "study_id", nullable = false)
	private Study study;

	@Column(name = "recorded_time", nullable = false)
	private Long recordedTime;

	@Column(name = "percentage", nullable = false)
	private Double percentage;
}
