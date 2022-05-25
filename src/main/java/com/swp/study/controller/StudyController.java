package com.swp.study.controller;

import com.swp.study.domain.StudyService;
import com.swp.study.dto.StudyLogResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/study")
public class StudyController {
	private final StudyService studyService;

	@ApiOperation(value = "study log 리스트", notes = "study id로 study logs를 모두 조회합니다.")
	@GetMapping(value = "/studylog/{studyId}")
	public List<StudyLogResponseDto> getStudyLog(@PathVariable Integer studyId) {
		return studyService.getStudyLog(studyId);
	}
}
