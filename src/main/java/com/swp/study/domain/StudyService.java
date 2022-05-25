package com.swp.study.domain;


import com.swp.study.Exception.StudyNotFoundException;
import com.swp.study.dto.StudyLogResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StudyService {
	private final StudyRepository studyRepository;
	private final StudyLogRepository studyLogRepository;

	public List<StudyLogResponseDto> getStudyLog(Integer studyId) {
		Study study = studyRepository.findById(studyId)
				.orElseThrow(() -> new StudyNotFoundException("study 정보가 없습니다."));
		return studyLogRepository.findByStudy(study).stream()
				.map(StudyLogResponseDto::from)
				.collect(Collectors.toList());
	}
}
