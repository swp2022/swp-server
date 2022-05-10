package com.swp.auth.exception;

import com.swp.common.exception.ForbiddenException;

public class AccessFobiddenException extends ForbiddenException {

	public AccessFobiddenException(String message) {
		super(message);
	}
}
