package com.swp.board.exception;

import com.swp.common.exception.ForbiddenException;

public class CommentForbiddenException extends ForbiddenException {

	private static final String MESSAGE = "작성자나 관리자가 아닙니다";

	public CommentForbiddenException() {
		super(MESSAGE);
	}
}
