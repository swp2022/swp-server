package com.swp.auth.exception;

import com.swp.common.exception.UnauthorizedException;

public class ExpiredTokenException extends UnauthorizedException {

	public ExpiredTokenException(String message) {
		super(message);
	}
}
