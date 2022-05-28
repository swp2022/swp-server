package com.swp.study.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swp.study.domain.Study;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyResponseDto {
	private Integer studyId;
	private Double finalPercentage;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Seoul")
	private LocalDateTime startAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "Asia/Seoul")
	private LocalDateTime endAt;

	public static StudyResponseDto from(Study study) {
		return StudyResponseDto.builder()
			.studyId(study.getStudyId())
			.finalPercentage(study.getFinalPercentage())
			.startAt(study.getCreatedAt())
			.endAt(study.getEndAt())
			.build();
	}
}