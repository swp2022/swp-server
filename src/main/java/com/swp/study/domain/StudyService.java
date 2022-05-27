package com.swp.study.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swp.auth.dto.JwtUserDetails;
import com.swp.study.Exception.StudyFinishedException;
import com.swp.study.Exception.StudyNotFoundException;
import com.swp.study.Exception.StudyUserNotMatchException;
import com.swp.study.dto.StudyLogPostDto;
import com.swp.study.dto.StudyLogResponseDto;
import com.swp.study.dto.StudyResponseDto;
import com.swp.user.domain.User;
import com.swp.user.domain.UserRepository;
import com.swp.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudyService {
	private final StudyRepository studyRepository;
	private final StudyLogRepository studyLogRepository;
	private final UserRepository userRepository;

	public List<StudyLogResponseDto> getStudyLog(Integer studyId) {
		Study study = studyRepository.findById(studyId)
			.orElseThrow(() -> new StudyNotFoundException("study 정보가 없습니다."));
		return studyLogRepository.findByStudy(study).stream()
			.map(StudyLogResponseDto::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public StudyResponseDto createStudy(JwtUserDetails userDetails) {
		User user = userRepository.findByProviderAndProviderId(userDetails.getProvider(), userDetails.getUsername())
			.orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));

		Study study = studyRepository.save(Study.builder().user(user).build());

		return StudyResponseDto.from(study);
	}

	@Transactional
	public StudyResponseDto finishStudy(JwtUserDetails userDetails, Integer studyId) {
		Study study = getStudy(userDetails, studyId);
		study.finishStudy();
		return StudyResponseDto.from(study);
	}

	@Transactional
	public StudyLogResponseDto pushStudyLog(JwtUserDetails userDetails, Integer studyId,
		StudyLogPostDto studyLogPostDto) {
		Study study = getStudy(userDetails, studyId);

		StudyLog studyLog = studyLogRepository.save(StudyLog.builder()
			.study(study)
			.percentage(studyLogPostDto.getPercentage())
			.recordedTime(studyLogPostDto.getRecordedTime())
			.build());

		return StudyLogResponseDto.from(studyLog);
	}

	private Study getStudy(JwtUserDetails userDetails, Integer studyId) {
		User user = userRepository.findByProviderAndProviderId(userDetails.getProvider(), userDetails.getUsername())
			.orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));

		Study study = studyRepository.findById(studyId)
			.orElseThrow(() -> new StudyNotFoundException("study 정보가 없습니다."));

		if (!study.getUser().equals(user))
			throw new StudyUserNotMatchException();

		if (study.getEndAt() != null)
			throw new StudyFinishedException();

		return study;
	}
}
