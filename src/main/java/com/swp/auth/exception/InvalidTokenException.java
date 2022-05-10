package com.swp.auth.exception;

import com.swp.common.exception.ForbiddenException;

public class InvalidTokenException extends ForbiddenException {

	public InvalidTokenException(String message) {
		super(message);
	}
}
