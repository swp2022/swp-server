package com.swp.study.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swp.study.domain.StudyLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyLogResponseDto {
	private Integer studyLogId;
	private Long recordedTime;
	private Double percentage;

	public static StudyLogResponseDto from(StudyLog studyLog) {
		return StudyLogResponseDto.builder()
				.studyLogId(studyLog.getStudyLogId())
				.recordedTime(studyLog.getRecordedTime())
				.percentage(studyLog.getPercentage())
				.build();
	}
}
