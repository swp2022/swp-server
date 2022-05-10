package com.swp.auth.exception;

import com.swp.common.exception.ForbiddenException;

public class EmptyTokenException extends ForbiddenException {

	public EmptyTokenException(String message) {
		super(message);
	}
}
