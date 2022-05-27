package com.swp.study.Exception;

import com.swp.common.exception.ConflictException;

public class StudyFinishedException extends ConflictException {

	private static final String MESSAGE = "이미 종료된 스터디입니다";

	public StudyFinishedException() {
		super(MESSAGE);
	}
}
