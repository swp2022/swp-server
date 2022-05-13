package com.swp.auth.exception;

import com.swp.common.exception.BadRequestException;

public class InvalidTokenException extends BadRequestException {

	public InvalidTokenException(String message) {
		super(message);
	}
}
