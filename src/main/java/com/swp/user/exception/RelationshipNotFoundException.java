package com.swp.user.exception;

import com.swp.common.exception.NotFoundException;

public class RelationshipNotFoundException extends NotFoundException {
	public RelationshipNotFoundException(String message) {
		super(message);
	}
}
