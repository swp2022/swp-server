package com.swp.board.exception;

import com.swp.common.exception.NotFoundException;

public class BoardNotFoundException extends NotFoundException {

	public BoardNotFoundException(String message) {
		super(message);
	}
}
