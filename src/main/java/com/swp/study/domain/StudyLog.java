package com.swp.study.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "study_logs")
@Getter
@NoArgsConstructor
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

	@Builder
	public StudyLog(Study study, Long recordedTime, Double percentage) {
		this.study = study;
		this.recordedTime = recordedTime;
		this.percentage = percentage;
	}
}
