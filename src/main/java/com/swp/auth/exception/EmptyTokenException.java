package com.swp.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class EmptyTokenException extends AuthenticationException {

	public EmptyTokenException(String msg) {
		super(msg);
	}
}
