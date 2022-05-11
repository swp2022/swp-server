package com.swp.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenException extends AuthenticationException {

	public TokenException(String message) {
		super(message);
	}
}
