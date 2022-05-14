package com.swp.auth.exception;

import com.swp.common.exception.UnauthorizedException;

public class InvalidTokenException extends UnauthorizedException {

	public InvalidTokenException(String message) {
		super(message);
	}
}
