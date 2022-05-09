package com.swp.user.exception;

import com.swp.common.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {

	public UserNotFoundException(String message) {
		super(message);
	}
}
