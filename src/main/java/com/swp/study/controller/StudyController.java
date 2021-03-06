package com.swp.study.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swp.auth.dto.JwtUserDetails;
import com.swp.study.domain.StudyService;
import com.swp.study.dto.StudyFinishDto;
import com.swp.study.dto.StudyLogPostDto;
import com.swp.study.dto.StudyLogResponseDto;
import com.swp.study.dto.StudyResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

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

	@ApiOperation(value = "스터디 생성", notes = "서버 시각 기준, 만들어진 Study Id 반환")
	@PostMapping
	public StudyResponseDto createStudy(@AuthenticationPrincipal JwtUserDetails userDetails) {
		return studyService.createStudy(userDetails);
	}

	@ApiOperation(value = "스터디 종료", notes = "서버 시각 기준 종료")
	@PostMapping("/{studyId}/finish")
	public StudyResponseDto finishStudy(@AuthenticationPrincipal JwtUserDetails userDetails,
		@PathVariable Integer studyId, @RequestBody StudyFinishDto finishDto) {
		return studyService.finishStudy(userDetails, studyId, finishDto);
	}

	@ApiOperation(value = "스터디 로그 삽입", notes = "기록 시각, 집중도를 싣어야 합니다")
	@PostMapping("/{studyId}/push")
	public StudyLogResponseDto pushStudyLog(@AuthenticationPrincipal JwtUserDetails userDetails,
		@PathVariable Integer studyId,
		@RequestBody StudyLogPostDto studyLogPostDto) {
		return studyService.pushStudyLog(userDetails, studyId, studyLogPostDto);
	}

	@ApiOperation(value = "자신의 모든 스터디 가져오기", notes = "자신의 모든 **종료된** 스터디를 가져옵니다")
	@GetMapping
	public List<StudyResponseDto> getFinishedStudy(@AuthenticationPrincipal JwtUserDetails userDetails,
		@RequestParam(name = "page", defaultValue = "0") Integer page,
		@RequestParam(name = "size", defaultValue = "20") Integer size) {
		return studyService.getFinishedStudy(userDetails, page, size);
	}
}
