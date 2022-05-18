package com.swp.board.exception;

import com.swp.common.exception.NotFoundException;

public class CommentNotFoundException extends NotFoundException {

	private static final String MESSAGE = "없는 댓글입니다";

	public CommentNotFoundException() {
		super(MESSAGE);
	}
}
