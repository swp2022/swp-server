package com.swp.study.Exception;

import com.swp.common.exception.ForbiddenException;

public class StudyUserNotMatchException extends ForbiddenException {

	private static final String MESSAGE = "요청한 유저가 스터디 유저와 다릅니다";

	public StudyUserNotMatchException() {
		super(MESSAGE);
	}
}
